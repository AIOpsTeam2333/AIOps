package com.aiops.api.config.mybatis.typehandler;

import com.aiops.api.entity.vo.response.KeyValue;
import com.aiops.api.entity.vo.response.StringList;
import com.aiops.api.entity.vo.response.TraceSpanLogData;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import org.apache.ibatis.javassist.compiler.ast.StringL;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Shuaiyu Yao
 * @create 2020-06-02 23:08
 **/
@MappedJdbcTypes({JdbcType.LONGVARCHAR, JdbcType.VARCHAR})
@MappedTypes({StringList.class})
public class StringListHandler extends BaseTypeHandler<StringList> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, StringList strings, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, "");
    }

    @Override
    public StringList getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String str = resultSet.getString(s);
        return string2StringList(str);
    }

    @Override
    public StringList getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String str = resultSet.getString(i);
        return string2StringList(str);
    }

    @Override
    public StringList getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String str = callableStatement.getString(i);
        return string2StringList(str);
    }


    /**
     * 将json转换为TraceSpanLogData对象
     *
     * @param original
     * @return
     */
    private StringList string2StringList(String original) {
        if (original == null || "".equals(original)) {
            return new StringList();
        }

        try {
            return new StringList(Lists.newArrayList(original.split("~~~")));
        } catch (Exception e) {
            e.printStackTrace();
            return new StringList();
        }
    }
}
