package sw.melody.modules.docker.service;

import sw.melody.modules.docker.entity.SampleEntity;

import java.util.List;
import java.util.Map;

public interface SampleService {
    SampleEntity queryObject(Long id);

    List<SampleEntity> queryList(Map<String, Object> map);

    SampleEntity queryObjectByLocationSick(String location, Long sickId);

    int queryTotal(Map<String, Object> map);

    void save(SampleEntity sampleEntity);

    void update(SampleEntity sampleEntity);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}
