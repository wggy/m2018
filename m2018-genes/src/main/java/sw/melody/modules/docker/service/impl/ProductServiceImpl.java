package sw.melody.modules.docker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw.melody.modules.docker.dao.ProductDao;
import sw.melody.modules.docker.entity.ProductEntity;
import sw.melody.modules.docker.service.ProductService;

import java.util.List;
import java.util.Map;

/***
 * Created by ping on 2018-7-26
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Override
    public void save(ProductEntity entity) {
        productDao.save(entity);
    }

    @Override
    public void update(ProductEntity entity) {
        productDao.update(entity);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        productDao.deleteBatch(ids);
    }

    @Override
    public List<ProductEntity> queryList(Map<String, Object> map) {
        return productDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return productDao.queryTotal(map);
    }

    @Override
    public ProductEntity queryObject(Long id) {
        return productDao.queryObject(id);
    }
}
