package com.aiops.api.common.enums;

import com.aiops.api.common.exception.NoSuchKpiException;
import io.swagger.annotations.ApiModel;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 14:36
 **/
@ApiModel("所有的kpi 的类型, 不区分大小写")
public enum KpiType {

    PERCENTILE,
    P50,
    P75,
    P90,
    P95,
    P99,
    HEATMAP,
    RESPONSE_TIME,
    THROUGHPUT,
    SLA,
    APDEX_SCORE,
    HEAP,
    MAX_HEAP,
    NON_HEAP,
    MAX_NON_HEAP,
    YOUNG_GC_TIME,
    OLD_GC_TIME,
    YOUNG_GC_COUNT,
    OLD_GC_COUNT,
    CPU,
    CLR_CPU,
    CLR_GC_GEN0,
    CLR_GC_GEN1,
    CLR_GC_GEN2,
    CLR_HEAP;

    public String databaseName() {
        return name().toLowerCase();
    }

    public static KpiType find(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            try {
                return valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e1) {
                throw new NoSuchKpiException(name);
            }
        }
    }
}
