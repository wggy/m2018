package sw.melody.modules.job.dao;

import sw.melody.modules.job.entity.ScheduleJobEntity;
import sw.melody.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定时任务
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:29:57
 */
@Mapper
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
