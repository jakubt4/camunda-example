package com.example.camunda.services;

import com.example.camunda.CamundaProcessApplication;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Finish implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(Finish.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOG.info("Process {} is done.", CamundaProcessApplication.PROCESS_NAME);
    }
}
