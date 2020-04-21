package com.aiops.api.config.mybatis.typehandler;

import com.aiops.api.entity.vo.response.KeyValue;
import com.aiops.api.entity.vo.response.TraceSpanLogData;
import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-21 21:12
 */
@MappedJdbcTypes({JdbcType.LONGVARCHAR, JdbcType.VARCHAR})
@MappedTypes({TraceSpanLogData.class})
public class TraceSpanLogDataHandler extends BaseTypeHandler<TraceSpanLogData> {

    /**
     * 不写入, 省略
     *
     * @param preparedStatement
     * @param i
     * @param keyValues
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, TraceSpanLogData keyValues, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, "");
    }

    @Override
    public TraceSpanLogData getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);
        return string2TraceSpanLogData(string);
    }

    @Override
    public TraceSpanLogData getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);
        return string2TraceSpanLogData(string);
    }

    @Override
    public TraceSpanLogData getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);
        return string2TraceSpanLogData(string);
    }

    /**
     * 将json转换为TraceSpanLogData对象
     *
     * @param original
     * @return
     */
    private TraceSpanLogData string2TraceSpanLogData(String original) {
        if (original == null || "".equals(original)) {
            return null;
        }

        try {
            List<KeyValue> list = JSONArray.parseArray(original, KeyValue.class);
            TraceSpanLogData result = new TraceSpanLogData();
            result.addAll(list);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
