package sw.melody.modules.job.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sw.melody.common.exception.RRException;
import sw.melody.modules.job.entity.SnpEntity;
import sw.melody.modules.job.entity.SnpFormatEntity;
import sw.melody.modules.job.service.SnpFormatService;
import sw.melody.modules.job.service.SnpService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/***
 * Created by ping on 2018/6/20
 */
@Slf4j
@Component("geneSnpTask")
public class GeneSnpTask {
    // 表头开始标记
    private static final String BEGIN = "#CHROM";
    private static final String TAB = "\t";
    private static final String LF = "\n";

    @Autowired
    private SnpService snpService;
    @Autowired
    private SnpFormatService snpFormatService;


    // 固定表头
    private static String[] fixed_cols = new String[]{"CHROM", "POS", "ID", "REF", "ALT", "QUAL", "FILTER", "INFO"};


    public void parseSnp(String fileName) throws Exception {
        File f = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String str = null;
        // 读取数据行标记
        boolean flag = false;
        String[] dynamicHeader = null;
        while ((str = reader.readLine()) != null) {
            if (StringUtils.isEmpty(str)) {
                continue;
            } else if (str.startsWith(BEGIN)) {
                // 解析表头
                // 去掉开始#
                str = str.replace("#", "");
                if (!checkHeader(str)) {
                    String fixedColsStr = StringUtils.join(fixed_cols, TAB);
                    String msg = MessageFormat.format("VCF固定标题不匹配，该文件标题为：{0}，固定格式标题为：{1}", str, fixedColsStr);
                    throw new RRException(msg);
                }
                dynamicHeader = parseDynamicRow(str);
                flag = true;
                continue;
            }
            if (flag) {
                SnpEntity snpEntity = parseLine(str);
                String[] dynamicRow = parseDynamicRow(str);
                List<SnpFormatEntity> formatList = parseFormatList(dynamicHeader, dynamicRow);
                snpService.save(snpEntity, formatList);
            }
        }
    }

    private SnpEntity parseLine(String line) {
        if (StringUtils.isEmpty(line)) {
            return null;
        }
        String[] cols = StringUtils.split(line, TAB);
        SnpEntity entity = new SnpEntity(cols);
        return entity;
    }

    private boolean checkHeader(String line) {
        String[] headers = line.split(TAB);
        int size = fixed_cols.length;
        String[] vcfFixedHeaders = new String[size];
        System.arraycopy(headers, 0, vcfFixedHeaders, 0, size);
        for (int i=0; i<size; i++) {
            if (!fixed_cols[i].equals(vcfFixedHeaders[i])) {
                return false;
            }
        }
        return true;
    }

    private String[] parseDynamicRow(String line) {
        String[] headers = line.split(TAB);
        int dynamicSize = headers.length - fixed_cols.length;
        String[] vcfDynamicHeaders = new String[dynamicSize];
        System.arraycopy(headers, fixed_cols.length, vcfDynamicHeaders, 0, dynamicSize);
        return vcfDynamicHeaders;
    }

    private List<SnpFormatEntity> parseFormatList(String[] dynamicHeader, String[] dynamicRow) {
        List<SnpFormatEntity> list = new ArrayList<>();
        if (dynamicHeader == null || dynamicHeader.length < 1) {
            return list;
        }
        int sickNum = dynamicHeader.length - 1;
        for (int i=0; i<sickNum; i++) {
            SnpFormatEntity entity = new SnpFormatEntity();
            entity.setSick(dynamicHeader[i+1]);
            entity.setFormat(dynamicRow[0]);
            entity.setFormatVal(dynamicRow[i+1]);
            entity.setFormatRate(dynamicRow[i+1]);
            list.add(entity);
        }
        return list;
    }
}
