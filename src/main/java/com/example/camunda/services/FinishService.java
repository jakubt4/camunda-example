package com.example.camunda.services;

import com.example.camunda.CamundaProcessApplication;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FinishService implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(FinishService.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOG.info("Process {} is done.", CamundaProcessApplication.PROCESS_NAME);
    }
}
