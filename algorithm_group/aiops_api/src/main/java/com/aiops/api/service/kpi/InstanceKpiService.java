package com.aiops.api.service.kpi;

import com.aiops.api.dao.InstanceKpiDao;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-23 11:05
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstanceKpiService {

    private final InstanceKpiDao instanceKpiDao;

    public List<CrossAxisGraphPoint> getInstanceResponseTime(Duration duration, Integer instanceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        return instanceKpiDao.queryResponseTime(instanceId, step, from, to);
    }

    public List<CrossAxisGraphPoint> getInstanceThroughput(Duration duration, Integer instanceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        return instanceKpiDao.queryThroughput(instanceId, step, from, to);
    }

    public List<CrossAxisGraphPoint> getInstanceSla(Duration duration, Integer instanceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        return instanceKpiDao.querySla(instanceId, step, from, to);
    }

    public List<CrossAxisGraphPoint> getInstanceCpu(Duration duration, Integer instanceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        return instanceKpiDao.queryCpu(instanceId, step, from, to);
    }

    public List<SimpleOrderNode> getServiceInstanceThroughput(Duration duration, Integer serviceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        return instanceKpiDao.queryServiceInstanceThroughput(serviceId, step, from, to);
    }

    public List<MemoryPoint> getJvmHeap(Duration duration, Integer instanceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        return instanceKpiDao.queryJvmHeap(instanceId, step, from, to);
    }

    public List<MemoryPoint> getNonJvmHeap(Duration duration, Integer instanceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        return instanceKpiDao.queryNonJvmHeap(instanceId, step, from, to);
    }

    public GcTime getGcTime(Duration duration, Integer instanceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        GcTime result = new GcTime();
        result.setOldGCTime(instanceKpiDao.queryOldGcTime(instanceId, step, from, to));
        result.setYoungGCTime(instanceKpiDao.queryYoungGcTime(instanceId, step, from, to));
        return result;
    }

    public GcCount getGcCount(Duration duration, Integer instanceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        GcCount result = new GcCount();
        result.setOldGCCount(instanceKpiDao.queryOldGcCount(instanceId, step, from, to));
        result.setYoungGCCount(instanceKpiDao.queryYoungGcCount(instanceId, step, from, to));
        return result;
    }

    public ClrGC getClrGc(Duration duration, Integer instanceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        ClrGC result = new ClrGC();
        result.setClrGCGen0(instanceKpiDao.queryClrGcGen0(instanceId, step, from, to));
        result.setClrGCGen1(instanceKpiDao.queryClrGcGen1(instanceId, step, from, to));
        result.setClrGCGen2(instanceKpiDao.queryClrGcGen2(instanceId, step, from, to));
        return result;
    }

    public List<CrossAxisGraphPoint> getClrCpu(Duration duration, Integer instanceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        return instanceKpiDao.queryClrCpu(instanceId, step, from, to);
    }

    public List<CrossAxisGraphPoint> getClrHeap(Duration duration, Integer instanceId) {
        String step = duration.getStepName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        return instanceKpiDao.queryClrHeap(instanceId, step, from, to);
    }
}
