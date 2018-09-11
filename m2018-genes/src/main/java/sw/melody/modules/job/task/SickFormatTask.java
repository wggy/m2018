package sw.melody.modules.job.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sw.melody.common.utils.Constant;
import sw.melody.modules.job.entity.SnpFormatEntity;
import sw.melody.modules.job.service.SnpFormatService;
import sw.melody.modules.job.utils.Arith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ping
 * @create 2018-08-09 15:00
 **/
@Slf4j
@Component("sickFormatTask")
public class SickFormatTask {

    private static final String sZero = "0";

    @Autowired
    private SnpFormatService snpFormatService;

    public void parseFormat() {

        // 每次取出100条数据
        Map<String, Object> paramsMap = new HashMap<>();
        int limit = 100;
        int index = 0;
        paramsMap.put("limit", 100);
        paramsMap.put("offset", 0);

        while (true) {
            List<SnpFormatEntity> formatEntityList = snpFormatService.queryList(paramsMap);
            if (CollectionUtils.isEmpty(formatEntityList)) {
                log.info("查无数据或任务执行完成...");
                break;
            }
            parserMutation(formatEntityList);
            snpFormatService.updateBatch(formatEntityList);
            paramsMap.put("offset", (++index) * limit);
        }

    }

    public void parserMutation(List<SnpFormatEntity> formatEntityList) {
        for (SnpFormatEntity entity : formatEntityList) {
            String formatVal = entity.getFormatVal();
            if (StringUtils.isEmpty(formatVal)) {
                log.error("SnpFormatEntity: {} 的format_val取值为空", entity.getId());
                continue;
            }

            try {
                String[] formatValArray = formatVal.split(":");
                String mutationMode = Constant.MutationMode.getMode(formatValArray[0]);
                String mutationAd = formatValArray[1].replace(",", "/");
                String mutationRate = calcRate(formatValArray[1].split(",")[1], formatValArray[2]);
                entity.setMutationMode(mutationMode);
                entity.setMutationAd(mutationAd);
                entity.setMutationRate(mutationRate);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("SnpFormatEntity解析失败", e);
            }

        }
    }

    private String calcRate(String mutationRate1, String mutationRate2) {
        if (sZero.equals(mutationRate2)) {
            return null;
        }
        return Arith.div(Arith.toDouble(mutationRate1), Arith.toDouble(mutationRate2), 2) + "";
    }

    private static String calcRate1(String mutationRate1, String mutationRate2) {
        if (sZero.equals(mutationRate2)) {
            return null;
        }
        return Arith.div(Arith.toDouble(mutationRate1), Arith.toDouble(mutationRate2), 2) + "";
    }

    public static void main(String[] args) {
        String s = "1/1:0,9:9:27:404,27,0";
        String[] formatValArray = s.split(":");
        String mutationMode = Constant.MutationMode.getMode(formatValArray[0]);
        String mutationAd = formatValArray[1].replace(",", "/");
        String mutationRate = calcRate1(formatValArray[1].split(",")[1], formatValArray[2]);
        System.out.println(mutationMode);
        System.out.println(mutationAd);
        System.out.println(mutationRate);
    }

}
