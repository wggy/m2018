package sw.melody.modules.docker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw.melody.modules.docker.dao.SampleDao;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.service.SampleService;

import java.util.List;
import java.util.Map;

@Service
public class SampleServiceImpl implements SampleService {
    @Autowired
    private SampleDao sampleDao;
    @Override
    public SampleEntity queryObject(Long id) {
        return sampleDao.queryObject(id);
    }

    @Override
    public List<SampleEntity> queryList(Map<String, Object> map) {
        return sampleDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sampleDao.queryTotal(map);
    }

    @Override
    public void save(SampleEntity sampleEntity) {
        sampleDao.save(sampleEntity);
    }

    @Override
    public void update(SampleEntity sampleEntity) {
        sampleDao.update(sampleEntity);
    }

    @Override
    public void delete(Long id) {
        sampleDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        sampleDao.deleteBatch(ids);
    }
}
