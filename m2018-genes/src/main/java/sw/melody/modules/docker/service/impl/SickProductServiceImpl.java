package sw.melody.modules.docker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.melody.modules.docker.dao.SickProductDao;
import sw.melody.modules.docker.service.SickProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Created by ping on 2018-7-27
 */
@Service
public class SickProductServiceImpl implements SickProductService {
    @Autowired
    private SickProductDao sickProductDao;

    @Override
    @Transactional
    public void saveOrUpdate(Long sickId, List<Long> productIdList) {
        if(productIdList.size() == 0){
            return ;
        }

        //先删除用户与角色关系
        sickProductDao.delete(sickId);
        //保存用户与角色关系
        Map<String, Object> map = new HashMap<>();
        map.put("sickId", sickId);
        map.put("productIdList", productIdList);
        sickProductDao.save(map);
    }

    @Override
    public List<Long> queryProductIdList(Long sickId) {
        return sickProductDao.queryProductIdList(sickId);
    }

    @Override
    public void delete(Long sickId) {
        sickProductDao.delete(sickId);
    }
}
