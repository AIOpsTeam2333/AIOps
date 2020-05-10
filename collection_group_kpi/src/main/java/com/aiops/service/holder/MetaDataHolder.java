package com.aiops.service.holder;

import java.util.ArrayList;
import java.util.List;

public class MetaDataHolder {

    private static List<String> services = new ArrayList<>();
    private static List<String> endpoints = new ArrayList<>();
    private static List<String> instances;

    public static List<String> getServices() {
        return services;
    }

    public static void setServices(List<String> services) {
        MetaDataHolder.services = services;
    }

    public static List<String> getEndpoints() {
        return endpoints;
    }

    public static void setEndpoints(List<String> endpoints) {
        MetaDataHolder.endpoints = endpoints;
    }

    public static List<String> getInstances() {
        return instances;
    }

    public static void setInstances(List<String> instances) {
        MetaDataHolder.instances = instances;
    }
}
