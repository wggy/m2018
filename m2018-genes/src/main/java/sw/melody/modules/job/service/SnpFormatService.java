package sw.melody.modules.job.service;

import sw.melody.modules.job.entity.SnpEntity;
import sw.melody.modules.job.entity.SnpFormatEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月28日 上午9:55:32
 */
public interface SnpFormatService {

	/**
	 * 根据ID
	 */
	SnpFormatEntity queryObject(Integer id);

	/**
	 * 查询列表
	 */
	List<SnpFormatEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);

	void save(SnpFormatEntity snpEntity);

	void saveBatch(List<SnpFormatEntity> snpEntity);
	void updateBatch(List<SnpFormatEntity> snpEntity);
}
