package sw.melody.modules.docker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw.melody.modules.docker.dao.OSSFileDao;
import sw.melody.modules.docker.entity.OSSFileEntity;
import sw.melody.modules.docker.service.OSSFileService;
import sw.melody.modules.docker.util.ConcurrentHashSet;
import sw.melody.modules.docker.util.OSSFileCacheUtil;

import java.util.HashMap;
import java.util.List;

/**
 * @author ping
 * @create 2018-12-24 17:05
 **/
@Service
public class OSSFileServiceImpl implements OSSFileService {

    @Autowired
    private OSSFileDao ossFileDao;

    @Override
    public List<OSSFileEntity> getList() {
        List<OSSFileEntity> ossFileRepo = OSSFileCacheUtil.getOssFileRepo();
        if (ossFileRepo != null) {
            return ossFileRepo;
        }
        return ossFileDao.queryList(new HashMap<>());
    }

    @Override
    public OSSFileEntity queryObject(Long id) {
        return ossFileDao.queryObject(id);
    }

    @Override
    public void saveBatch(List<OSSFileEntity> list) {
        ossFileDao.saveBatch(list);
    }

    @Override
    public void save(OSSFileEntity entity) {
        ossFileDao.save(entity);
    }

    @Override
    public void deleteAll() {
        ossFileDao.deleteAll();
    }
}
