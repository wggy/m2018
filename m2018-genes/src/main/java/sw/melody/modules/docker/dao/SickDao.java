package sw.melody.modules.docker.dao;

import org.apache.ibatis.annotations.Mapper;
import sw.melody.modules.docker.entity.SickEntity;
import sw.melody.modules.sys.dao.BaseDao;

@Mapper
public interface SickDao extends BaseDao<SickEntity> {
    SickEntity queryObjectByCode(String sickCode);
}
