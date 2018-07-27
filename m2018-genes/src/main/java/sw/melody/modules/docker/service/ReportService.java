package sw.melody.modules.docker.service;

import sw.melody.modules.docker.entity.ReportEntity;

import java.util.List;
import java.util.Map;

/***
 * Created by ping on 2018-7-26
 */
public interface ReportService {
    List<ReportEntity> queryList(Map<String, Object> map);
    int queryTotal(Map<String, Object> map);
}
