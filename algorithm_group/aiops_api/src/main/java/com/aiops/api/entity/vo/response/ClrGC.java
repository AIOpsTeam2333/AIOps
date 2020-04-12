package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-09 10:50
 */
@Data
public class ClrGC {

    private List<CrossAxisGraphPoint> clrGCGen0;
    private List<CrossAxisGraphPoint> clrGCGen1;
    private List<CrossAxisGraphPoint> clrGCGen2;

}
