package sw.melody.modules.docker.dao;

import org.apache.ibatis.annotations.Mapper;
import sw.melody.modules.docker.entity.SickProductEntity;
import sw.melody.modules.sys.dao.BaseDao;

import java.util.List;

/***
 * Created by ping on 2018-7-27
 */
@Mapper
public interface SickProductDao extends BaseDao<SickProductEntity> {
    List<Long> queryProductIdList(Long sickId);
}
