package com.aiops.service;

import com.aiops.dao.MetricEndpointDAO;
import com.aiops.model.MetricEndpointDO;
import com.aiops.query.QueryHelper;
import com.aiops.query.enums.Step;
import com.aiops.query.holder.MetaDataHolder;
import com.aiops.query.model.Duration;
import com.aiops.query.model.MetricCondition;
import com.aiops.query.model.QueryStatement;
import com.aiops.query.parser.MetricEndpointParser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class MetricEndpointCollector {

    private static String[] metrics = {"endpoint_relation_resp_time", "endpoint_sla", "endpoint_cpm", "endpoint_p99",
            "endpoint_p95", "endpoint_p90", "endpoint_p75", "endpoint_p50"};
    private static String queryType = "getLinearIntValues";

    public void collectEndpoint(Step step){
        List<String> endpoints = MetaDataHolder.getEndpoints();
        try {
            for (String metric : metrics){
                for (String endpointId : endpoints){
                    collectSingle(queryType, metric, endpointId, step);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Collect Endpoint Info By " + step + ": " + new Date());
    }

    @Autowired
    private MetricEndpointDAO metricEndpointDAO;

    public void collectSingle(String querytype, String metric, String endpointId, Step step) throws ParseException {

        QueryStatement statement = QueryHelper.getQueryStatement(querytype);
        statement.addDuration(new Duration(new Date(), step));
        statement.addMetricConditon(new MetricCondition(metric, endpointId));

        JSONObject json = QueryHelper.query(statement);

        MetricEndpointDO metricEndpointDO = MetricEndpointParser.parseResponse(step, json);
        metricEndpointDAO.insertMetricEndpointDO(metricEndpointDO, metric, step);
//        System.out.print("Metric: "+metric+", EndpointId: "+endpointId+", TimeStamp: " + metricEndpointDO.getTimestamp() + ", Value: " + metricEndpointDO.getValue()+"\n");
    }

}
