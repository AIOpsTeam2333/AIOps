package com.aiops.dao;

import com.aiops.model.MetricInstanceDO;
import com.aiops.query.enums.Step;
import com.aiops.util.DBUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class MetricInstanceDAO {

    public void insertMetricInstanceDO(MetricInstanceDO metricInstanceDO, String metric, Step step) {
        Connection conn = DBUtil.getConnection();

        try {
            PreparedStatement pstm = conn.prepareStatement(prepareInsertSql(metric, step));
            pstm.setInt(1, metricInstanceDO.getInstanceId());
            pstm.setDouble(2, metricInstanceDO.getValue());
            pstm.setTimestamp(3, metricInstanceDO.getTimestamp());
            pstm.setDouble(4, metricInstanceDO.getPredict());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String prepareInsertSql(String metric, Step step){
        //使Skywalking预设的metric名称与数据库表名保持一致
        if (metric.equals("service_instance_sla")){
            metric = "instance_sla";
        }
        else if(metric.equals("service_instance_resp_time")){
            metric = "instance_response_time";
        }
        else if(metric.equals("service_instance_cpm")){
            metric = "instance_throughput";
        }
        else if(metric.equals("instance_jvm_cpu")){
            metric = "instance_cpu";
        }
        else if(metric.equals("instance_jvm_memory_heap")){
            metric = "instance_heap";
        }
        else if(metric.equals("instance_jvm_memory_noheap")){
            metric = "instance_non_heap";
        }
        else if(metric.equals("instance_jvm_memory_heap_max")){
            metric = "instance_max_heap";
        }
        else if(metric.equals("instance_jvm_memory_noheap_max")){
            metric = "instance_max_non_heap";
        }
        else if(metric.equals("instance_jvm_young_gc_time")){
            metric = "instance_young_gc_time";
        }
        else if(metric.equals("instance_jvm_old_gc_time")){
            metric = "instance_old_gc_time";
        }
        else if(metric.equals("instance_jvm_young_gc_count")){
            metric = "instance_young_gc_count";
        }
        else if(metric.equals("instance_jvm_old_gc_count")){
            metric = "instance_old_gc_count";
        }

        return "INSERT INTO `kpi_" + metric + "_" + step.toString().toLowerCase() + "` (`service_instance_id`, `value`, `time`, `predict`) VALUES (?, ?, ?, ?);\n";
    }
}
