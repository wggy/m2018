package sw.melody.modules.docker.service;

import java.util.List;

/***
 * Created by ping on 2018-7-27
 */
public interface SickProductService {
    void saveOrUpdate(Long sickId, List<Long> productIdList);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryProductIdList(Long sickId);

    void delete(Long sickId);
}
