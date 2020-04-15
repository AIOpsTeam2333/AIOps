package com.aiops.api.config;

import com.aiops.api.entity.vo.request.Paging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-15 19:46
 */
@Configuration
public class PagingConfig {

    @Value("${aiops.paging.size}")
    private Integer defaultPageSize;

    /**
     * 默认分页信息, 第一页, 页容量在配置中
     *
     * @return
     */
    @Bean
    public Paging defaultPaging() {
        Paging paging = new Paging();
        paging.setPageNum(1);
        paging.setPageSize(defaultPageSize);
        return paging;
    }

    @Bean
    public Paging noPaging() {
        Paging paging = new Paging();
        paging.setPageNum(1);
        paging.setPageSize(0);
        return paging;
    }
}
