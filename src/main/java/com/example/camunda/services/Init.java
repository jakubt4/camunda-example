package com.example.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Init implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(Init.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOG.info("[INIT] Init done {} wait.", (boolean) execution.getVariable("wait") ? "with" : "without");
    }
}
