package sw.melody.modules.job.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sw.melody.common.utils.Constant;

/***
 * Created by ping on 2018/6/20
 */
@Slf4j
@Component("geneIndelTask")
public class GeneIndelTask extends GeneAbsTask {

    public void parseIndel(String filename) throws Exception {
        super.parse(filename);
    }

    @Override
    public String getGeneType() {
        return Constant.DataType.Indel.name();
    }
}
