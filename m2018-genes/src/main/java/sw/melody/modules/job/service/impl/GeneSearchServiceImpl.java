package sw.melody.modules.job.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw.melody.modules.docker.dao.GeneSearchDao;
import sw.melody.modules.docker.entity.GeneSearchEntity;
import sw.melody.modules.job.service.GeneSearchService;

import java.util.List;
import java.util.Map;

/**
 * @author ping
 * @create 2018-08-09 17:34
 **/
@Service
public class GeneSearchServiceImpl implements GeneSearchService {
    @Autowired
    private GeneSearchDao geneSearchDao;

    @Override
    public List<GeneSearchEntity> queryList(Map<String, Object> paramsMap) {
        List<GeneSearchEntity> list = geneSearchDao.queryList(paramsMap);

        return geneSearchDao.queryList(paramsMap);
    }

    @Override
    public int queryTotal(Map<String, Object> paramsMap) {
        return geneSearchDao.queryTotal(paramsMap);
    }
}
