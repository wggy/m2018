package sw.melody.modules.docker.service;

import sw.melody.modules.docker.entity.GeneSearchEntity;
import sw.melody.modules.docker.entity.SickRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * @author wange
 */
public interface GeneSearchService {

    List<GeneSearchEntity> queryList(Map<String, Object> paramsMap);

    int queryTotal(Map<String, Object> paramsMap);

    SickRelationEntity querySickRelation(Map<String, Object> paramsMap);

    int queryTotalCount();

}
