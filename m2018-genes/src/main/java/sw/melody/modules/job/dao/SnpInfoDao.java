package sw.melody.modules.job.dao;

import org.apache.ibatis.annotations.Mapper;
import sw.melody.modules.job.entity.SnpEntity;
import sw.melody.modules.job.entity.SnpInfoEntity;
import sw.melody.modules.sys.dao.BaseDao;

/***
 * Created by ping on 2018/6/22
 */
@Mapper
public interface SnpInfoDao extends BaseDao<SnpInfoEntity> {
}
