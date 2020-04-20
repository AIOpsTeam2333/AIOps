package com.aiops.processdata.entity.span;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/3 20:42
 */
@Data
@NoArgsConstructor
public class Log_Info {
    private Long time;
    private List<Log_Data> data;
}
