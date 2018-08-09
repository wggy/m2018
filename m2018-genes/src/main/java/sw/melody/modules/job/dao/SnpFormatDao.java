package sw.melody.modules.job.dao;

import org.apache.ibatis.annotations.Mapper;
import sw.melody.modules.job.entity.SnpFormatEntity;
import sw.melody.modules.sys.dao.BaseDao;

import java.util.List;

/***
 * Created by ping on 2018/6/22
 * @author wange
 */
@Mapper
public interface SnpFormatDao extends BaseDao<SnpFormatEntity> {
    void updateBatch(List<SnpFormatEntity> list);
}
