package com.aiops.processdata.dao;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.entity.span.Ref_Info;
import com.aiops.processdata.entity.span.Span_Data;
import com.aiops.processdata.po.span.RefPO;
import com.aiops.processdata.service.Init;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/18 0:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class RefRepositoryTest {
    @Autowired
    private RefRepository refRepository;
    @Autowired
    private Init init;

    @Test
    public void findRefByConent() {
        Span_Data span_data = init.initSpan();
        Ref_Info ref_info = span_data.getData().getQueryTrace().getSpans().get(1).getRefs().get(0);
        RefPO refPO = refRepository.findRefByConent(ref_info);
        System.out.println(refPO);
        ref_info.setType("t");
        refPO = refRepository.findRefByConent(ref_info);
        System.out.println(refPO);
    }

    @Test
    public void findRefsBySpanId() {
        List<RefPO> refPOList = refRepository.findRefsBySpanId("");
        System.out.println(refPOList);
        refPOList = refRepository.findRefsBySpanId("2.63.15867631492160003/2.225.15867631494360000/0");
        System.out.println(refPOList);
    }
}
