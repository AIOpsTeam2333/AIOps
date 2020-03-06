package com.aiops.api.service.kpi;

import com.aiops.api.common.type.KpiType;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 16:11
 */
@Service
public class KpiHelper {

    /**
     * 将字符串包含的kpi转换为Kps Set
     *
     * @param s
     * @return
     */
    public Set<KpiType> splitKpi(String s) {
        if (s == null || s.equals("")) {
            //不筛选, 返回全部
            return new HashSet<>(Arrays.asList(KpiType.values()));
        }

        Set<String> splitted = new HashSet<>(Arrays.asList(s.split(",")));
        Set<KpiType> result = new HashSet<>();

        for (String strKpi : splitted) {
            try {
                KpiType kpiType = KpiType.find(strKpi);
                result.add(kpiType);
            } catch (Exception ignored) {
            }
        }
        return result;
    }

}
