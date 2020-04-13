package com.AIOps.Leonarda.service;

/**
 * @author: leonard lian
 * @description: Memory
 * @date: create in 18:07 2020-04-08
 */
public interface GraphqlCollection {
    void collectTracesAndSpans();

    void collectServiceAndInstance();

    void collectEndpoint();
}
