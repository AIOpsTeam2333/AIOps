package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-09 10:52
 */
@Data
public class GcTime {
    private List<CrossAxisGraphPoint> youngGCTime;
    private List<CrossAxisGraphPoint> oldGCTime;

}
