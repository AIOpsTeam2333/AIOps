package com.aiops.processdata.service;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.entity.span.Span_Data;
import com.aiops.processdata.po.span.SpanPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/18 2:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class SpanTest {
    @Autowired
    private Init init;

    @Autowired
    private Span span;

    @Test
    public void insert(){
        Span_Data span_data=init.initSpan();
        List<SpanPO> spanPOList=span.save(span_data);
        System.out.println(spanPOList);
    }
}
