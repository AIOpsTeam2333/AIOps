package com.aiops.dao;

import com.aiops.model.MetricAllDO;
import com.aiops.query.enums.Step;
import com.aiops.util.DBUtil;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class MetricAllDAO {

    public void insertMetricAllDO(MetricAllDO metricAllDO, String metric, Step step) {
        Connection conn = DBUtil.getConnection();

        try {
            PreparedStatement pstm = conn.prepareStatement(prepareInsertSql(metric, step));
            pstm.setDouble(1, metricAllDO.getValue());
            pstm.setTimestamp(2, metricAllDO.getTimestamp());
            pstm.setDouble(3, metricAllDO.getPredict());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String prepareInsertSql(String metric, Step step){
        return "INSERT INTO `kpi_" + metric + "_"
                + step.toString().toLowerCase()
                + "` (`value`, `time`, `predict`) VALUES (?, ?, ?);\n";
    }

    public void insertHeatMap(double numOfSteps, double value, Timestamp timestamp, Step step){
        String sql = "INSERT INTO `kpi_all_heatmap_"
                + step.toString().toLowerCase()
                + "` (`step`, `num_of_steps`, `time`, `value`) VALUES (?, ?, ?, ?);\n";

        Connection conn = DBUtil.getConnection();

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setDouble(1, 100);
            pstm.setDouble(2, numOfSteps);
            pstm.setTimestamp(3, timestamp);
            pstm.setDouble(4, value);
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
