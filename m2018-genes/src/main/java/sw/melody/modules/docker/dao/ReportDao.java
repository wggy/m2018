package sw.melody.modules.docker.dao;

import org.apache.ibatis.annotations.Mapper;
import sw.melody.modules.docker.entity.ReportEntity;
import sw.melody.modules.sys.dao.BaseDao;

import java.util.List;

/***
 * Created by ping on 2018-7-26
 */
@Mapper
public interface ReportDao extends BaseDao<ReportEntity> {
    List<String> queryDyUrlList();
}
