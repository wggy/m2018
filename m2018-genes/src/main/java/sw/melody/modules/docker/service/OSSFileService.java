package sw.melody.modules.docker.service;

import sw.melody.modules.docker.entity.OSSFileEntity;

import java.util.List;

/**
 * @author wange
 */
public interface OSSFileService {

    List<OSSFileEntity> getList();

    OSSFileEntity queryObject(Long id);

    void saveBatch(List<OSSFileEntity> list);

    void save(OSSFileEntity entity);

    void deleteAll();
}
