package com.aiops.service;

import com.aiops.dao.MetricInstanceDAO;
import com.aiops.model.MetricInstanceDO;
import com.aiops.query.QueryHelper;
import com.aiops.query.enums.Step;
import com.aiops.query.holder.MetaDataHolder;
import com.aiops.query.model.Duration;
import com.aiops.query.model.MetricCondition;
import com.aiops.query.model.QueryStatement;
import com.aiops.query.parser.MetricInstanceParser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class MetricInstanceCollector {

    private static String[] metrics = {"service_instance_sla", "service_instance_resp_time", "service_instance_cpm",
            "instance_jvm_cpu", "instance_jvm_memory_heap", "instance_jvm_memory_noheap", "instance_jvm_memory_heap_max", "instance_jvm_memory_noheap_max",
            "instance_jvm_young_gc_time", "instance_jvm_old_gc_time", "instance_jvm_young_gc_count", "instance_jvm_old_gc_count"};
    private static String queryType = "getLinearIntValues";

    public void collectInstance(Step step){
        List<String> instances = MetaDataHolder.getInstances();
        Date date = new Date();
        try {
            for (String metric : metrics){
                for (String instanceId : instances){
                    collectSingle(queryType, metric, instanceId, step, date);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        try {
//            for (String metric : metrics){
//                    collectSingle(queryType, metric, "13", step);
//                }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        System.out.println("Collect Instance Info By " + step + ": " + new Date());
    }

    @Autowired
    private MetricInstanceDAO metricInstanceDAO;

    public void collectSingle(String querytype, String metric, String instanceId, Step step, Date date) throws ParseException {

        QueryStatement statement = QueryHelper.getQueryStatement(querytype);
        statement.addDuration(new Duration(date, step));
        statement.addMetricConditon(new MetricCondition(metric, instanceId));

        JSONObject json = QueryHelper.query(statement);

        MetricInstanceDO metricInstanceDO = MetricInstanceParser.parseResponse(step, json);
        metricInstanceDAO.insertMetricInstanceDO(metricInstanceDO, metric, step);
//        System.out.print("Metric: "+metric+", InstanceId: "+instanceId+", TimeStamp: " + metricInstanceDO.getTimestamp() + ", Value: " + metricInstanceDO.getValue()+"\n");
    }

}
