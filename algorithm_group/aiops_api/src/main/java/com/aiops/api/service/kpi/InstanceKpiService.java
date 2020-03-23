package com.aiops.api.service.kpi;

import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.SimpleOrderNode;
import com.aiops.api.mapper.InstanceKpiMapper;
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

    private final InstanceKpiMapper instanceKpiMapper;

    public List<SimpleOrderNode> getServiceInstanceThroughput(Duration duration, Integer serviceId) {
        String step = duration.getStep().getName();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        return instanceKpiMapper.serviceInstanceThroughputDesc(serviceId, step, from, to);
    }

}
