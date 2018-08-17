package sw.melody.modules.docker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw.melody.modules.docker.dao.GeneSearchDao;
import sw.melody.modules.docker.dao.SickRelationDao;
import sw.melody.modules.docker.entity.GeneSearchEntity;
import sw.melody.modules.docker.entity.SickRelationEntity;
import sw.melody.modules.docker.service.GeneSearchService;

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
    @Autowired
    private SickRelationDao sickRelationDao;

    @Override
    public List<GeneSearchEntity> queryList(Map<String, Object> paramsMap) {
        List<GeneSearchEntity> list = geneSearchDao.queryList(paramsMap);
        list.forEach(item -> GeneSearchEntity.parseAttr(item));
        return list;
    }

    @Override
    public int queryTotal(Map<String, Object> paramsMap) {
        return geneSearchDao.queryTotal(paramsMap);
    }

    @Override
    public SickRelationEntity querySickRelation(Map<String, Object> paramsMap) {
        return sickRelationDao.queryObject(paramsMap);
    }

    @Override
    public int queryTotalCount() {
        return geneSearchDao.queryTotalCount();
    }
}
