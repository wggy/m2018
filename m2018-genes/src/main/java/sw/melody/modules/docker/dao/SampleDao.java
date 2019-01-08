package sw.melody.modules.docker.dao;

import org.apache.ibatis.annotations.Mapper;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.sys.dao.BaseDao;

import java.util.List;

@Mapper
public interface SampleDao extends BaseDao<SampleEntity> {
    SampleEntity queryObjectByMd5(String md5);
    SampleEntity queryObjectByGuid(String guid);
    SampleEntity queryObjectBySickId(Long sickId);
    void deleteByFlag(Long id);
    void resetTriggerStatus(Long sampleId);

    List<SampleEntity> queryListSickId(Long sickId);

    int queryTotalBySickId(Long sickId);

}
