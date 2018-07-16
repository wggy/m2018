package sw.melody.modules.docker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw.melody.modules.docker.dao.SickDao;
import sw.melody.modules.docker.entity.SickEntity;
import sw.melody.modules.docker.service.SickService;

import java.util.List;
import java.util.Map;

@Service
public class SickServiceImpl implements SickService {
    @Autowired
    private SickDao sickDao;
    @Override
    public SickEntity queryObject(Long id) {
        return sickDao.queryObject(id);
    }

    @Override
    public List<SickEntity> queryList(Map<String, Object> map) {
        return sickDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sickDao.queryTotal(map);
    }

    @Override
    public void save(SickEntity sickEntity) {
        sickDao.save(sickEntity);
    }

    @Override
    public void update(SickEntity sickEntity) {
        sickDao.update(sickEntity);
    }

    @Override
    public void delete(Long id) {
        sickDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        sickDao.deleteBatch(ids);
    }
}