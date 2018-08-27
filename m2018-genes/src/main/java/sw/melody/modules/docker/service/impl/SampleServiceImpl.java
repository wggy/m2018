package sw.melody.modules.docker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sw.melody.modules.docker.dao.SampleDao;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.service.SampleService;

import java.util.*;

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
    public SampleEntity queryObjectByLocationSick(String location, Long sickId) {
        Map<String, Object> map = new HashMap<>();
        map.put("location", location);
        map.put("sickId", sickId);
        List<SampleEntity> list = sampleDao.queryList(map);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        list.sort((o1, o2) -> {
            if (o1.getUploadStartTime() == null || o2.getUploadStartTime() == null) {
                return 0;
            }
            long t1 = o1.getUploadStartTime().getTime();
            long t2 = o2.getUploadStartTime().getTime();
            return Long.compare(t2, t1);
        });
        return list.get(0);
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

    @Override
    public SampleEntity queryObjectByMd5(String md5) {
        return sampleDao.queryObjectByMd5(md5);
    }
}
