package com.example.camunda.services;

import com.example.camunda.CamundaProcessApplication;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitService implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(InitService.class);

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOG.info("[INIT] Init done {} wait.", (boolean) execution.getVariable("wait") ? "with" : "without");
    }

    public ProcessInstance initProcess(boolean isWaiting) {
        final Map<String, Object> params = new HashMap<>();
        params.put("wait", isWaiting);

        return runtimeService.startProcessInstanceByKey(CamundaProcessApplication.PROCESS_NAME, params);
    }
}
