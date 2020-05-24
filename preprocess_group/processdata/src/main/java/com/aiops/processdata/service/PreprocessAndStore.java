package com.aiops.processdata.service;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/5/20 21:16
 *
 * 这个类负责为收集组提供接口
 */
public interface PreprocessAndStore {

    /**
     * 用于注册instance
     * 传入实例json，存入数据库
     * @param instanceJson 存有instance的json字符串
     * @return
     */
    boolean saveInstance(String instanceJson);

    /**
     * 用于注册service
     * 传入服务json，存入数据库
     * @param serviceJson
     * @return
     */
    boolean saveService(String serviceJson);

    /**
     * 用于注册endpoint
     * 传入端点json，存入数据库
     * @param endpointJson
     * @return
     */
    boolean saveEndpoint(String endpointJson);


    /**
     * 用于注册trace和segment
     * 传入trace json，存入数据库
     * @param traceJson
     * @return
     */
    boolean saveTrace(String traceJson);

    /**
     * 用于注册span
     * 传入span json，存入数据库
     * @param spanJson
     * @return
     */
    boolean saveSpan(String spanJson);
}
