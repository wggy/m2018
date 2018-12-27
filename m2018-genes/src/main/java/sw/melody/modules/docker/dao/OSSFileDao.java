package sw.melody.modules.docker.dao;

import org.apache.ibatis.annotations.Mapper;
import sw.melody.modules.docker.entity.OSSFileEntity;
import sw.melody.modules.sys.dao.BaseDao;

/**
 * @author wange
 */
@Mapper
public interface OSSFileDao extends BaseDao<OSSFileEntity> {
    void deleteAll();
}
