package com.aiops.processdata.service;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/4 21:10
 */
public interface JsonToBean {
    /**
     * json转换javabean方法
     *
     * @param filePath 文件路径
     * @param tClass   目标bean类型
     * @param <T>      目标bean类型
     * @return 目标bean对象
     */
    <T> T convert(String filePath, Class<T> tClass);
}
