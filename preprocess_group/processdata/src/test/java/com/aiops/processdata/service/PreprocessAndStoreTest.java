package com.aiops.processdata.service;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.utils.MyUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ResourceBundle;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/5/24 14:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class PreprocessAndStoreTest {

    @Autowired
    private PreprocessAndStore preprocessAndStore;

    @Autowired
    private ResourceBundle bundle;

    @Test
    public void saveInstance() {
        String filepath = getClass().getClassLoader().getResource(bundle.getString("SERVICEINSTANCE_FILEPATH")).getPath();
        String instanceJson= MyUtils.readFile(filepath);
        preprocessAndStore.saveInstance(instanceJson);
    }

    @Test
    public void saveService() {
        String filepath = getClass().getClassLoader().getResource(bundle.getString("SERVICE_FILEPATH")).getPath();

        String serviceJson=MyUtils.readFile(filepath);
        preprocessAndStore.saveService(serviceJson);

    }

    @Test
    public void saveEndpoint() {
        String filepath = getClass().getClassLoader().getResource(bundle.getString("ENDPOINT_FILEPATH")).getPath();

        String endpointJson=MyUtils.readFile(filepath);
        preprocessAndStore.saveEndpoint(endpointJson);

    }

    @Test
    public void saveTrace() {
        String filepath = getClass().getClassLoader().getResource(bundle.getString("TRACE_FILEPATH")).getPath();

        String traceJson=MyUtils.readFile(filepath);
        preprocessAndStore.saveTrace(traceJson);
    }

    @Test
    public void saveSpan() {
        String filepath = getClass().getClassLoader().getResource(bundle.getString("SPAN_FILEPATH")).getPath();

        String spanJson=MyUtils.readFile(filepath);
        preprocessAndStore.saveSpan(spanJson);
    }
}
