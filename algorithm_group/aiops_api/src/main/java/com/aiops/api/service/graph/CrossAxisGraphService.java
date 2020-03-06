package com.aiops.api.service.graph;

import com.aiops.api.common.type.ClassificationType;
import com.aiops.api.common.type.KpiType;
import com.aiops.api.common.type.StatisticsStep;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 14:23
 */
public interface CrossAxisGraphService {

    List<CrossAxisGraphPoint> getCommonCrossAxisGraph(ClassificationType classificationType, KpiType kpiType, StatisticsStep statisticsStep);

}
