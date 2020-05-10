package com.aiops.query.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MetaDataParser {

    //数据格式：
    // {
    //    "data": {
    //        "getAllServices": [
    //            {
    //                "id": "2",
    //                "name": "SkyWalking"
    //            },
    //            {
    //                "id": "3",
    //                "name": "Turbine-Stream"
    //            }
    //        ]
    //    }
    //}
    public static List<String> parseAllServices(JSONObject response){
        List<String> ids = new ArrayList<>();
        JSONArray values = response.getJSONObject("data").getJSONArray("getAllServices");

        for (int i=0; i<values.size(); i++){
            ids.add(values.getJSONObject(i).getString("id"));
        }
        return ids;
    }

    public static List<String> parseAllInstances(JSONObject response){
        List<String> ids = new ArrayList<>();
        JSONArray values = response.getJSONObject("data").getJSONArray("getServiceInstances");

        for (int i=0; i<values.size(); i++){
            ids.add(values.getJSONObject(i).getString("id"));
        }
        return ids;
    }

    public static List<String> parseAllEndpoints(JSONObject response){
        List<String> ids = new ArrayList<>();
        JSONArray values = response.getJSONObject("data").getJSONArray("searchEndpoint");

        for (int i=0; i<values.size(); i++){
            ids.add(values.getJSONObject(i).getString("id"));
        }
        return ids;
    }
}
