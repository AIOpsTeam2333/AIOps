package com.aiops.dao;

import com.aiops.model.MetricAllDO;
import com.aiops.query.enums.Step;
import com.aiops.util.DBUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
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
        return "INSERT INTO `kpi_" + metric + "_" + step.toString().toLowerCase() + "` (`value`, `time`, `predict`) VALUES (?, ?, ?);\n";
    }
}
