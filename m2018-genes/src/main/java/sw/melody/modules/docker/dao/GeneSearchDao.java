package sw.melody.modules.docker.dao;

import org.apache.ibatis.annotations.Mapper;
import sw.melody.modules.docker.entity.GeneSearchEntity;
import sw.melody.modules.sys.dao.BaseDao;

import java.util.List;

/***
 * Created by ping on 2018-7-26
 * @author wange
 */
@Mapper
public interface GeneSearchDao extends BaseDao<GeneSearchEntity> {
    int queryTotalCount(Long sickId);
    List<GeneSearchEntity> queryListByIds(Long[] ids);
}
