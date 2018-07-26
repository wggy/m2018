package sw.melody.modules.docker.dao;

import org.apache.ibatis.annotations.Mapper;
import sw.melody.modules.docker.entity.ProductEntity;
import sw.melody.modules.sys.dao.BaseDao;

/***
 * Created by ping on 2018-7-26
 */
@Mapper
public interface ProductDao extends BaseDao<ProductEntity> {
}
