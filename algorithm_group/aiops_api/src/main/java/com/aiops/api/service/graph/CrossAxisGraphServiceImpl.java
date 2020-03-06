package com.aiops.api.service.graph;

import com.aiops.api.common.type.ClassificationType;
import com.aiops.api.common.type.KpiType;
import com.aiops.api.common.type.StatisticsStep;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 16:06
 */
@Service
public class CrossAxisGraphServiceImpl implements CrossAxisGraphService {


    @Override
    public List<CrossAxisGraphPoint> getCommonCrossAxisGraph(ClassificationType classificationType, KpiType kpiType, StatisticsStep statisticsStep) {
        return new ArrayList<>();
    }
}
