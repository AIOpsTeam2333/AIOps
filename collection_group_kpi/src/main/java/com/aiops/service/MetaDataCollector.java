package com.aiops.service;

import com.aiops.query.QueryHelper;
import com.aiops.query.enums.Step;
import com.aiops.query.holder.MetaDataHolder;
import com.aiops.query.model.Duration;
import com.aiops.query.model.QueryStatement;
import com.aiops.query.parser.MetaDataParser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MetaDataCollector {

    static {
        refreshMetadata();
    }

    private static void refreshMetadata(){
        collectServices();
        collectInstances(MetaDataHolder.getServices());
        collectEndpoints(MetaDataHolder.getServices());
    }

    private static void collectEndpoints(List<String> services){
        List<String> endpointIds = new ArrayList<>();
        for (String id : services) {
            //创建查询
            QueryStatement statement = QueryHelper.getQueryStatement("searchEndpoint");
            statement.addParam("keyword", "");
            statement.addParam("serviceId", id);
            statement.addParam("limit", 1000);
            JSONObject json = QueryHelper.query(statement);

            List<String> ids = MetaDataParser.parseAllEndpoints(json);
            endpointIds.addAll(ids);
        }

        //写入
        MetaDataHolder.setEndpoints(endpointIds);
        System.out.print("Refresh Endpoint MetaData Successfully\n");
    }

    private static void collectInstances(List<String> services){
        List<String> instanceIds = new ArrayList<>();
        for (String id : services) {
            //创建查询
            QueryStatement statement = QueryHelper.getQueryStatement("getServiceInstances");
            statement.addDuration(new Duration(new Date(), Step.DAY));
            statement.addParam("serviceId", id);
            JSONObject json = QueryHelper.query(statement);

            List<String> ids = MetaDataParser.parseAllInstances(json);
            instanceIds.addAll(ids);
        }

        //写入
        MetaDataHolder.setInstances(instanceIds);
        System.out.print("Refresh Instance MetaData Successfully\n");
    }

    private static void collectServices(){
        //创建查询
        QueryStatement statement = QueryHelper.getQueryStatement("getAllServices");
        statement.addDuration(new Duration(new Date(), Step.DAY));
        JSONObject json = QueryHelper.query(statement);

        //写入
        List<String> serviceIds = MetaDataParser.parseAllServices(json);
        MetaDataHolder.setServices(serviceIds);

        System.out.print("Refresh Service MetaData Successfully\n");
    }
}
