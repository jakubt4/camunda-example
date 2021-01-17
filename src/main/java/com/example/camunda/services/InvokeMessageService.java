package com.example.camunda.services;

import java.util.HashMap;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class InvokeMessageService {

    @Autowired
    private RuntimeService runtimeService;

    public MessageCorrelationResult invokeMessageForProcess(String processId, String message) {
        return runtimeService
                .createMessageCorrelation(message)
                .processInstanceId(processId)
                .setVariables(new HashMap<String, Object>())
                .correlateWithResult();
    }
}
