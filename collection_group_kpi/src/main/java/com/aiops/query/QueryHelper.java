package com.aiops.query;

import com.aiops.query.model.QueryStatement;
import com.aiops.util.HttpUtil;
import com.alibaba.fastjson.JSONObject;

public class QueryHelper {

    private static final String GRAGHQL_URL = "http://localhost:12800/graphql";

    public static JSONObject query(QueryStatement statement){
        return HttpUtil.doPost(GRAGHQL_URL, statement.toJSONString());
    }

    private static final String getLinearIntValues = "query get($metriccondition: MetricCondition!,$duration : Duration!){\n" +
            "    \tgetLinearIntValues(metric: $metriccondition, duration: $duration){\n" +
            "    \t\t\tvalues{\n" +
            "    \t\t\t\tid,\n" +
            "    \t\t\t\tvalue\n" +
            "    \t\t\t}\n" +
            "    \t}\n" +
            "    }";

    private static final String getAllServices = "query get($duration : Duration!){\n" +
            "    \tgetAllServices(duration: $duration){\n" +
            "    \t\tid,\n" +
            "    \t\tname\n" +
            "    \t}\n" +
            "    }";

    private static final String getServiceInstances = "query get($duration : Duration!, $serviceId: ID!){\n"+
            "    \tgetServiceInstances(duration: $duration,  serviceId: $serviceId){\n"+
            "    \t\tid,\n"+
            "    \t\tname,\n"+
            "    \t\tinstanceUUID\n"+
            "    \t}\n"+
            "    }";

    private static final String searchEndpoint = "query get($keyword : String!, $serviceId: ID!, $limit: Int!){\n"+
            "    \tsearchEndpoint(keyword: $keyword, serviceId: $serviceId, limit: $limit){\n"+
            "    \t\tid,\n"+
            "    \t\tname\n"+
            "    \t}\n"+
            "    }";

    private static final String getThermodynamic = "query get($metriccondition: MetricCondition!,$duration : Duration!){" +
            "   getThermodynamic(metric: $metriccondition, duration: $duration){nodes,axisYStep}}";

    public static QueryStatement getQueryStatement(String functionName){
        if (functionName.equals("getLinearIntValues"))
            return new QueryStatement(getLinearIntValues);
        else if (functionName.equals("getAllServices"))
            return new QueryStatement(getAllServices);
        else if (functionName.equals("getServiceInstances"))
            return new QueryStatement(getServiceInstances);
        else if (functionName.equals("searchEndpoint"))
            return new QueryStatement(searchEndpoint);
        else if (functionName.equals("getThermodynamic"))
            return new QueryStatement(getThermodynamic);
        else
            return new QueryStatement("");
    }
}
