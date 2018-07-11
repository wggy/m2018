package sw.melody.modules.job.service;

import io.swagger.models.auth.In;
import sw.melody.modules.job.entity.ScheduleJobEntity;
import sw.melody.modules.job.entity.SnpEntity;
import sw.melody.modules.job.entity.SnpFormatEntity;
import sw.melody.modules.job.entity.SnpInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月28日 上午9:55:32
 */
public interface SnpService {

	/**
	 * 根据ID，查询定时任务
	 */
	SnpEntity queryObject(Integer id);

	/**
	 * 查询定时任务列表
	 */
	List<SnpEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存
	 */
	void save(SnpEntity snpEntity, SnpInfoEntity entity, List<SnpFormatEntity> list);
}
