package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-26 16:32
 */
@Data
public class GcCount {
    private List<CrossAxisGraphPoint> youngGCCount;
    private List<CrossAxisGraphPoint> oldGCCount;
}
