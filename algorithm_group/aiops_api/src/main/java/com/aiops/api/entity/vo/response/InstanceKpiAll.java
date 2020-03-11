package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 11:14
 */
@Data
public class InstanceKpiAll {

    private List<CrossAxisGraphPoint> instanceResponseTime;
    private List<CrossAxisGraphPoint> instanceThroughput;
    private List<CrossAxisGraphPoint> instanceSLA;
    private List<CrossAxisGraphPoint> instanceCPU;
    private List<CrossAxisGraphPoint> clrCPU;
    private CrlGC crlGC;
    private List<CrossAxisGraphPoint> clrHeap;
    private GcTime gcTime;

    private List<MemoryPoint> heap;
    private List<MemoryPoint> nonHeap;

}
