package sw.melody.modules.docker.service;

import sw.melody.modules.docker.entity.ProductEntity;
import java.util.List;
import java.util.Map;

/***
 * Created by ping on 2018-7-26
 */
public interface ProductService {

    void save(ProductEntity config);

    void update(ProductEntity config);

    void deleteBatch(Long[] ids);

    List<ProductEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    ProductEntity queryObject(Long id);
}
