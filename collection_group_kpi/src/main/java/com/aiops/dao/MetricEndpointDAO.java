package com.aiops.dao;

import com.aiops.model.MetricEndpointDO;
import com.aiops.query.enums.Step;
import com.aiops.util.DBUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class MetricEndpointDAO {

    public void insertMetricEndpointDO(MetricEndpointDO metricEndpointDO, String metric, Step step) {
        Connection conn = DBUtil.getConnection();

        try {
            PreparedStatement pstm = conn.prepareStatement(prepareInsertSql(metric, step));
            pstm.setInt(1, metricEndpointDO.getEndpointId());
            pstm.setDouble(2, metricEndpointDO.getValue());
            pstm.setTimestamp(3, metricEndpointDO.getTimestamp());
            pstm.setDouble(4, metricEndpointDO.getPredict());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String prepareInsertSql(String metric, Step step){
        //使Skywalking预设的metric名称与数据库表名保持一致
        if (metric.equals("endpoint_relation_resp_time")){
            metric = "endpoint_response_time";
        }
        else if(metric.equals("endpoint_cpm")){
            metric = "endpoint_throughput";
        }

        return "INSERT INTO `kpi_" + metric + "_" + step.toString().toLowerCase() + "` (`service_endpoint_id`, `value`, `time`, `predict`) VALUES (?, ?, ?, ?);\n";
    }
}
