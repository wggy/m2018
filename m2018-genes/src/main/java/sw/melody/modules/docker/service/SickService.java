package sw.melody.modules.docker.service;

import sw.melody.modules.docker.entity.SickEntity;

import java.util.List;
import java.util.Map;

public interface SickService {
    SickEntity queryObject(Long id);

    SickEntity queryObjectByCode(String sickCode);

    List<SickEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SickEntity sysOss);

    void update(SickEntity sysOss);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}
