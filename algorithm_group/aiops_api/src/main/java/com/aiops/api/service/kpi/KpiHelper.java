package com.aiops.api.service.kpi;

import com.aiops.api.common.enums.KpiType;
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
    public KpiIndicator splitKpi(String s) {
        if (s == null || s.equals("")) {
            //不筛选, 返回全部
            return new KpiIndicator(new HashSet<>());
        }

        Set<String> split = new HashSet<>(Arrays.asList(s.split(",")));
        Set<KpiType> kpiTypes = new HashSet<>();

        for (String strKpi : split) {
            try {
                KpiType kpiType = KpiType.find(strKpi);
                kpiTypes.add(kpiType);
            } catch (Exception ignored) {
            }
        }
        return new KpiIndicator(kpiTypes);
    }

}
