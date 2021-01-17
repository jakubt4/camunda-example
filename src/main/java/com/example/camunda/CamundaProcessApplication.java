package com.example.camunda;

import com.example.camunda.controllers.MainController;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class CamundaProcessApplication {

    public static final String PROCESS_NAME = "CamundaProcess";

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    public static void main(String[] args) {
        LOG.info("Starting application for {} camunda process..", PROCESS_NAME);
        SpringApplication.run(CamundaProcessApplication.class, args);
    }

}
