package sw.melody.modules.job.service.impl;

import org.apache.commons.collections.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sw.melody.modules.job.dao.SnpFormatDao;
import sw.melody.modules.job.entity.SnpFormatEntity;
import sw.melody.modules.job.service.SnpFormatService;

import java.util.ArrayList;
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

    @Override
    public void updateBatch(List<SnpFormatEntity> snpEntityList) {
        if (CollectionUtils.isEmpty(snpEntityList)) {
            return;
        }
        int size = snpEntityList.size();
        List<SnpFormatEntity> subList = new ArrayList<>();
        for (int i=0; i<size; i++) {
            subList.add(snpEntityList.get(i));
            if (subList.size() == 100) {
                snpFormatDao.updateBatch(subList);
                subList.clear();
            }
        }
        if (!CollectionUtils.isEmpty(subList)) {
            snpFormatDao.updateBatch(subList);
        }
    }
}
