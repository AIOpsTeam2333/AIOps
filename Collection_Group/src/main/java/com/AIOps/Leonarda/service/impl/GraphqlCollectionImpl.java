package com.AIOps.Leonarda.service.impl;

import com.AIOps.Leonarda.domain.Duration;
import com.AIOps.Leonarda.domain.TraceQueryCondition;
import com.AIOps.Leonarda.domain.Paging;
import com.AIOps.Leonarda.domain.QueryDuration;
import com.AIOps.Leonarda.domain.common.Step;
import com.AIOps.Leonarda.domain.trace.QueryOrder;
import com.AIOps.Leonarda.domain.trace.TraceState;
import com.AIOps.Leonarda.service.GraphqlCollection;
import com.AIOps.Leonarda.tools.JsonCreation;
import org.mountcloud.graphql.GraphqlClient;
import org.mountcloud.graphql.request.mutation.DefaultGraphqlMutation;
import org.mountcloud.graphql.request.mutation.GraphqlMutation;
import org.mountcloud.graphql.request.query.DefaultGraphqlQuery;
import org.mountcloud.graphql.request.query.GraphqlQuery;
import org.mountcloud.graphql.request.result.ResultAttributtes;
import org.mountcloud.graphql.response.GraphqlResponse;

import java.util.*;

/**
 * @author: leonard lian
 * @description: Memory
 * @date: create in 02:15 2020-04-05
 */
public class GraphqlCollectionImpl implements GraphqlCollection {

    public void collectTracesAndSpans(){
        String serverUrl = "http://localhost:12800/graphql";
        GraphqlClient graphqlClient = GraphqlClient.buildGraphqlClient(serverUrl);
        String queryMethodName = "queryBasicTraces";
        GraphqlQuery query = new DefaultGraphqlQuery(queryMethodName);
        query.getRequestParameter().addObjectParameter("condition",new TraceQueryCondition(new QueryDuration("2019-10","2020-07", Step.MONTH), TraceState.ALL, QueryOrder.BY_DURATION,new Paging(700)));
        ResultAttributtes traces = new ResultAttributtes("traces");
        traces.addResultAttributes("segmentId","endpointNames","duration","start","isError","traceIds");
        query.addResultAttributes(traces);
        try{
            GraphqlResponse response = graphqlClient.doQuery(query);
            Map map=response.getData();
//            List map1=(List) map.get("data");
//            String e=(String) ((Map) map1.get(0)).get("message");
//            System.out.println(e);
            List list=(List) ((Map)((Map)map.get("data")).get("queryBasicTraces")).get("traces");
            boolean flag= JsonCreation.createJsonFile(list,"/Users/Leonarda/Desktop/data/trace.json");
            System.out.println(flag);

            List result=new ArrayList();
            for(int i=0;i<list.size();i++){
                Map simpleT=(Map)list.get(i);
                List traceIds=(List) simpleT.get("traceIds");
                for (int j=0;j<traceIds.size();j++){
                    String traceId=(String)traceIds.get(j);
                    List spans=collectSpan(traceId);
                    result.addAll(spans);
                }
            }
            boolean flag2= JsonCreation.createJsonFile(result,"/Users/Leonarda/Desktop/data/span.json");
            System.out.println(flag2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void collectServiceAndInstance(){
        String serverUrl = "http://localhost:12800/graphql";
        GraphqlClient graphqlClient = GraphqlClient.buildGraphqlClient(serverUrl);
        String queryMethodName = "getAllServices";
        GraphqlQuery query = new DefaultGraphqlQuery(queryMethodName);
        query.getRequestParameter().addObjectParameter("duration",new Duration("2019-10","2020-07", Step.MONTH));
        query.addResultAttributes("id","name");
        try{
            GraphqlResponse response = graphqlClient.doQuery(query);
            Map map=response.getData();
//            List map1=(List) map.get("data");
//            String e=(String) ((Map) map1.get(0)).get("message");
//            System.out.println(e);
            List list=(List) ((Map)map.get("data")).get("getAllServices");
            boolean flag= JsonCreation.createJsonFile(list,"/Users/Leonarda/Desktop/data/service.json");
            System.out.println(flag);

            List serviceInstanceList=new ArrayList();
            for(int i=0;i<list.size();i++){
                Map simpleT=(Map)list.get(i);
                String id=(String) simpleT.get("id");
                serviceInstanceList.addAll(collectServiceInstance(id));
            }
            boolean flag2= JsonCreation.createJsonFile(serviceInstanceList,"/Users/Leonarda/Desktop/data/serviceInstance.json");
            System.out.println(flag2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void collectEndpoint(){
        String serverUrl = "http://localhost:12800/graphql";
        GraphqlClient graphqlClient = GraphqlClient.buildGraphqlClient(serverUrl);
        String queryMethodName = "getGlobalBrief";
        GraphqlQuery query = new DefaultGraphqlQuery(queryMethodName);
        query.getRequestParameter().addObjectParameter("duration",new Duration("2019-10","2020-07", Step.MONTH));
        query.addResultAttributes("numOfEndpoint");
        try{
            GraphqlResponse response = graphqlClient.doQuery(query);
            Map map=response.getData();

            List resultList=new ArrayList();
            int num=(Integer) ((Map)((Map)map.get("data")).get("getGlobalBrief")).get("numOfEndpoint");
            for(int i=1;i<=100;i++){
                Map eInfo=collectEndPointInfo(String.valueOf(i));
                if (eInfo!=null) {
                    resultList.add(collectEndPointInfo(String.valueOf(i)));
                }
            }
            boolean flag2= JsonCreation.createJsonFile(resultList,"/Users/Leonarda/Desktop/data/endpoint.json");
            System.out.println(flag2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Map collectEndPointInfo(String id){
        try {
            String serverUrl = "http://localhost:12800/graphql";
            GraphqlClient graphqlClient = GraphqlClient.buildGraphqlClient(serverUrl);
            String queryMethodName = "getEndpointInfo";
            GraphqlQuery query = new DefaultGraphqlQuery(queryMethodName);
            query.addParameter("endpointId",id);
            query.addResultAttributes("id","name","serviceId","serviceName");

            GraphqlResponse response = graphqlClient.doQuery(query);
            Map map=response.getData();
            Map map2=(Map) ((Map)(map.get("data"))).get("getEndpointInfo");
            return map2;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static List collectServiceInstance(String id){
        try {
            String serverUrl = "http://localhost:12800/graphql";
            GraphqlClient graphqlClient = GraphqlClient.buildGraphqlClient(serverUrl);
            String queryMethodName = "getServiceInstances";
            GraphqlQuery query = new DefaultGraphqlQuery(queryMethodName);
            query.getRequestParameter().addParameter("serviceId",id).addObjectParameter("duration",new Duration("2019-10","2020-07", Step.MONTH));
            query.addResultAttributes("id","name");

            ResultAttributtes attrAttr=new ResultAttributtes("attributes");
            attrAttr.addResultAttributes("name","value");
            query.addResultAttributes(attrAttr);

            query.addResultAttributes("language");
            GraphqlResponse response = graphqlClient.doQuery(query);
            Map map=response.getData();
            List list=(List) ((Map)map.get("data")).get("getServiceInstances");
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static List collectSpan(String id){
        try {
            String serverUrl = "http://localhost:12800/graphql";
            GraphqlClient graphqlClient = GraphqlClient.buildGraphqlClient(serverUrl);
            String queryMethodName = "queryTrace";
            GraphqlQuery query = new DefaultGraphqlQuery(queryMethodName);
            query.addParameter("traceId",id);
            ResultAttributtes span = new ResultAttributtes("spans");

            span.addResultAttributes("traceId","segmentId","spanId","parentSpanId");
            ResultAttributtes refsAttr=new ResultAttributtes("refs");
            refsAttr.addResultAttributes("traceId","parentSegmentId","parentSpanId","type");
            span.addResultAttributes(refsAttr);
            span.addResultAttributes(
            "serviceCode",
                    "startTime",
                    "endTime",
                    "endpointName",
                    "type",
                    "peer",
                    "component",
                    "isError",
                    "layer");

            ResultAttributtes tagsAttr=new ResultAttributtes("tags");
            tagsAttr.addResultAttributes("key","value");
            span.addResultAttributes(tagsAttr);

            ResultAttributtes logsAttr=new ResultAttributtes("logs");
            logsAttr.addResultAttributes("time");
            ResultAttributtes dataAttr=new ResultAttributtes("data");
            dataAttr.addResultAttributes("key","value");
            logsAttr.addResultAttributes(dataAttr);
            span.addResultAttributes(logsAttr);

            query.addResultAttributes(span);
            GraphqlResponse response = graphqlClient.doQuery(query);
            Map map=response.getData();
            List list=(List) ((Map)((Map)map.get("data")).get("queryTrace")).get("spans");
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
