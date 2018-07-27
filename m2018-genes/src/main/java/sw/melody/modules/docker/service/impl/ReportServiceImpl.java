package sw.melody.modules.docker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw.melody.modules.docker.dao.ReportDao;
import sw.melody.modules.docker.entity.ReportEntity;
import sw.melody.modules.docker.service.ReportService;

import java.util.List;
import java.util.Map;

/***
 * Created by ping on 2018-7-26
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public List<ReportEntity> queryList(Map<String, Object> map) {
        return reportDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return reportDao.queryTotal(map);
    }
}
