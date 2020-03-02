package com.aiops.api.config;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 16:12
 **/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {


}
