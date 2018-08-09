package sw.melody.modules.job.service;

import sw.melody.modules.docker.entity.GeneSearchEntity;

import java.util.List;
import java.util.Map;

public interface GeneSearchService {

    List<GeneSearchEntity> queryList(Map<String, Object> paramsMap);

    int queryTotal(Map<String, Object> paramsMap);

}
