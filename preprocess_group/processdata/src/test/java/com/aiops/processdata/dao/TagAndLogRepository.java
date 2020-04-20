package com.aiops.processdata.dao;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.po.span.LogPO;
import com.aiops.processdata.po.span.TagPO;
import json.trace_span.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/18 2:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class TagAndLogRepository {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private LogRepository logRepository;

    @Test
    public void findTagBySpanId(){
        TagPO tagPO=tagRepository.findTagBySpanId("2.76.15867660112138385/2.76.15867660112138384/0");
        System.out.println(tagPO);
        tagPO=tagRepository.findTagBySpanId("2.63.15867631492160003/2.225.15867631494360000/0");
        System.out.println(tagPO);
    }

    @Test
    public void findLogBySpanId(){
        List<LogPO> logPOList=logRepository.findLogsBySpanId("2.76.15867660112138385/2.76.15867660112138384/0");
        System.out.println(logPOList);
        logPOList=logRepository.findLogsBySpanId("2.63.15867631492160003/2.225.15867631494360000/0");
        System.out.println(logPOList);
    }
}
