package com.aiops.service;

import com.aiops.dao.MetricAllDAO;
import com.aiops.dao.MetricEndpointDAO;
import com.aiops.dao.MetricInstanceDAO;
import com.aiops.dao.MetricServiceDAO;
import com.aiops.model.MetricAllDO;
import com.aiops.model.MetricEndpointDO;
import com.aiops.model.MetricInstanceDO;
import com.aiops.model.MetricServiceDO;
import com.aiops.query.QueryHelper;
import com.aiops.query.enums.Step;
import com.aiops.query.model.QueryStatement;
import com.aiops.query.model.RetryQueryStatement;
import com.aiops.query.parser.MetricAllParser;
import com.aiops.query.parser.MetricEndpointParser;
import com.aiops.query.parser.MetricInstanceParser;
import com.aiops.query.parser.MetricServiceParser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class RetryCollector {
    private ConcurrentLinkedDeque<RetryQueryStatement> allRetryQueue = new ConcurrentLinkedDeque<>();
    private ConcurrentLinkedDeque<RetryQueryStatement> serviceRetryQueue = new ConcurrentLinkedDeque<>();
    private ConcurrentLinkedDeque<RetryQueryStatement> instanceRetryQueue = new ConcurrentLinkedDeque<>();
    private ConcurrentLinkedDeque<RetryQueryStatement> endpointRetryQueue = new ConcurrentLinkedDeque<>();

    @Autowired
    private MetricAllDAO metricAllDAO;
    @Autowired
    private MetricServiceDAO metricServiceDAO;
    @Autowired
    private MetricEndpointDAO metricEndpointDAO;
    @Autowired
    private MetricInstanceDAO metricInstanceDAO;

    public void retryUnsuccessfulQuery(){
        queryAll();
    }

    public void addRetryQueryStatement(QueryStatement statement, String metric, Step step){
        RetryQueryStatement retryStatement = new RetryQueryStatement(statement, metric, step);
        if (metric.startsWith("all")){
            allRetryQueue.add(retryStatement);
        }
        if (metric.startsWith("service")){
            serviceRetryQueue.add(retryStatement);
        }
        if (metric.startsWith("instance")){
            instanceRetryQueue.add(retryStatement);
        }
        if (metric.startsWith("endpoint")){
            endpointRetryQueue.add(retryStatement);
        }
        System.out.println("Add Retry Query: " + metric + " " + step);
    }

    private void queryAll(){
        //移除已经成功的查询
        removeSuccessfulQuery(allRetryQueue);
        for (RetryQueryStatement statement : allRetryQueue){
            try {
                //重试成功将RetryCount置0
                JSONObject json = QueryHelper.query(statement);
                MetricAllDO metricAllDO = MetricAllParser.parseResponse(statement.getStep(), json);
                metricAllDAO.insertMetricAllDO(metricAllDO, statement.getMetric(), statement.getStep());
                statement.setRetryCount(0);
            } catch (Exception e) {
                //重试失败将RetryCount减1
                statement.setRetryCount(statement.getRetryCount()-1);
            }
        }
    }

    private void queryService(){
        //移除已经成功的查询
        removeSuccessfulQuery(serviceRetryQueue);

        for (RetryQueryStatement statement : serviceRetryQueue){
            try {
                //重试成功将RetryCount置0
                JSONObject json = QueryHelper.query(statement);
                MetricServiceDO metricServiceDO = MetricServiceParser.parseResponse(statement.getStep(), json);
                metricServiceDAO.insertMetricServiceDO(metricServiceDO, statement.getMetric(), statement.getStep());
                statement.setRetryCount(0);
            } catch (Exception e) {
                //重试失败将RetryCount减1
                statement.setRetryCount(statement.getRetryCount()-1);
            }
        }
    }

    private void queryInstance(){
        //移除已经成功的查询
        removeSuccessfulQuery(instanceRetryQueue);

        for (RetryQueryStatement statement : instanceRetryQueue){
            try {
                //重试成功将RetryCount置0
                JSONObject json = QueryHelper.query(statement);
                MetricInstanceDO metricInstanceDO = MetricInstanceParser.parseResponse(statement.getStep(), json);
                metricInstanceDAO.insertMetricInstanceDO(metricInstanceDO, statement.getMetric(), statement.getStep());
                statement.setRetryCount(0);
            } catch (Exception e) {
                //重试失败将RetryCount减1
                statement.setRetryCount(statement.getRetryCount()-1);
            }
        }
    }

    private void queryEndpoint(){
        //移除已经成功的查询
        removeSuccessfulQuery(endpointRetryQueue);

        for (RetryQueryStatement statement : endpointRetryQueue){
            try {
                //重试成功将RetryCount置0
                JSONObject json = QueryHelper.query(statement);
                MetricEndpointDO metricEndpointDO = MetricEndpointParser.parseResponse(statement.getStep(), json);
                metricEndpointDAO.insertMetricEndpointDO(metricEndpointDO, statement.getMetric(), statement.getStep());
                statement.setRetryCount(0);
            } catch (Exception e) {
                //重试失败将RetryCount减1
                statement.setRetryCount(statement.getRetryCount()-1);
            }
        }
    }

    private void removeSuccessfulQuery(ConcurrentLinkedDeque<RetryQueryStatement> deque){
        deque.removeIf(statement -> statement.getRetryCount() <= 0);
    }
}
