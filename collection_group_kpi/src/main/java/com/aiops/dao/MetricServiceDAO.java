package com.aiops.dao;

import com.aiops.model.MetricServiceDO;
import com.aiops.query.enums.Step;
import com.aiops.util.DBUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class MetricServiceDAO {

    public void insertMetricServiceDO(MetricServiceDO metricServiceDO, String metric, Step step) {
        Connection conn = DBUtil.getConnection();

        try {
            PreparedStatement pstm = conn.prepareStatement(prepareInsertSql(metric, step));
            pstm.setInt(1, metricServiceDO.getServiceId());
            pstm.setDouble(2, metricServiceDO.getValue());
            pstm.setTimestamp(3, metricServiceDO.getTimestamp());
            pstm.setDouble(4, metricServiceDO.getPredict());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String prepareInsertSql(String metric, Step step){
        //使Skywalking预设的metric名称与数据库表名保持一致
        if (metric.equals("service_resp_time")){
            metric = "service_response_time";
        }
        else if(metric.equals("service_apdex")){
            metric = "service_apdex_score";
        }
        else if(metric.equals("service_cpm")){
            metric = "service_throughput";
        }

        return "INSERT INTO `kpi_" + metric + "_" + step.toString().toLowerCase() + "` (`service_id`, `value`, `time`, `predict`) VALUES (?, ?, ?, ?);\n";
    }
}
