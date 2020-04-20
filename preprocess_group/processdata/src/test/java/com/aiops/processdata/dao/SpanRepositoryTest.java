package com.aiops.processdata.dao;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.entity.span.Span_Data;
import com.aiops.processdata.entity.span.Span_Info;
import com.aiops.processdata.service.Init;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 21:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class SpanRepositoryTest {
    @Autowired
    private SpanRepository spanRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Init init;

    @Test
    public void insertSpan(){
        Span_Data span_data=init.initSpan();
        List<Span_Info> span_infoList=span_data.getData().getQueryTrace().getSpans();
        for(Span_Info span_info:span_infoList){
            System.out.println(span_info);
            System.out.println(spanRepository.insertSpan(span_info));
        }

    }

    @Test
    public void kvtest(){
        System.out.println("`key`");
        String s="insert into trace_key_value(`key`,`value`) VALUES (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException
                    {
                        PreparedStatement ps = jdbcTemplate.getDataSource()
                                .getConnection().prepareStatement(s,new String[]{ "key" ,"value"});
                        ps.setString(1, "站点号");
                        ps.setString(2, "我的名字");
                        return ps;
                    }
                }, keyHolder);
        System.out.println("自动插入id============================" + keyHolder.getKey().intValue());
    }
}
