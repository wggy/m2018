package sw.melody.modules.job.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw.melody.modules.job.dao.SnpFormatDao;
import sw.melody.modules.job.entity.SnpFormatEntity;
import sw.melody.modules.job.service.SnpFormatService;

import java.util.List;
import java.util.Map;

/***
 * Created by ping on 2018/6/22
 */
@Service
public class SnpFormatServiceImpl implements SnpFormatService {
    @Autowired
    private SnpFormatDao snpFormatDao;

    @Override
    public SnpFormatEntity queryObject(Integer id) {
        return snpFormatDao.queryObject(id);
    }

    @Override
    public List<SnpFormatEntity> queryList(Map<String, Object> map) {
        return snpFormatDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return snpFormatDao.queryTotal(map);
    }

    @Override
    public void save(SnpFormatEntity snpEntity) {
        snpFormatDao.save(snpEntity);
    }

    @Override
    public void saveBatch(List<SnpFormatEntity> list) {
        snpFormatDao.saveBatch(list);
    }
}
