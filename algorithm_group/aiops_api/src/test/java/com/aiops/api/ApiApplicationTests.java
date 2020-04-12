package com.aiops.api;

import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.HeatmapPoint;
import com.aiops.api.mapper.GlobalKpiMapper;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ApiApplicationTests {

    @Autowired
    private GlobalKpiMapper globalKpiMapper;

    @Test
    void contextLoads() {
    }

    @Test
    @Ignore
    void testGlobalKpiMapper() {
        Date from = new Date(0);
        Date to = new Date(Long.MAX_VALUE);
        List<HeatmapPoint> heatmapPoints = globalKpiMapper.selectHeatmap("hour", 100, from, to);
        System.out.println(heatmapPoints);
    }

    @Test
    @Ignore
    void testSelectCrossAxisKpi() {
        Date from = new Date(0);
        Date to = new Date(Long.MAX_VALUE);
        List<CrossAxisGraphPoint> crossAxisGraphPoints = globalKpiMapper.selectCrossAxisKpi("p50", "day", from, to);
        System.out.println(crossAxisGraphPoints);
    }
}
