package com.aiops.api.common.kpi;

import com.aiops.api.common.enums.KpiType;

import java.util.Set;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-26 11:51
 */
public class KpiIndicator {

    private final Set<KpiType> kpiTypes;


    KpiIndicator(Set<KpiType> kpiTypes) {
        this.kpiTypes = kpiTypes;
    }

    public boolean needKpiType(KpiType kpiType) {
        return kpiTypes.isEmpty() || kpiTypes.contains(kpiType);
    }

    public boolean needPercentile() {
        return needKpiType(KpiType.PERCENTILE) || needKpiType(KpiType.P50) || needKpiType(KpiType.P75) || needKpiType(KpiType.P90) || needKpiType(KpiType.P95) || needKpiType(KpiType.P99);
    }

    public boolean needHeap() {
        return needKpiType(KpiType.HEAP) || needKpiType(KpiType.MAX_HEAP);
    }

    public boolean needNonHeap() {
        return needKpiType(KpiType.NON_HEAP) || needKpiType(KpiType.MAX_NON_HEAP);
    }

    public boolean needGcTime() {
        return needKpiType(KpiType.YOUNG_GC_TIME) || needKpiType(KpiType.OLD_GC_TIME);
    }

    public boolean needGcCount() {
        return needKpiType(KpiType.YOUNG_GC_COUNT) || needKpiType(KpiType.OLD_GC_COUNT);
    }

    public boolean needClrGc() {
        return needKpiType(KpiType.CLR_GC_GEN0) || needKpiType(KpiType.CLR_GC_GEN1) || needKpiType(KpiType.CLR_GC_GEN2);
    }
}
