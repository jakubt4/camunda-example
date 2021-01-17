package com.example.camunda.controllers;

import static com.example.camunda.utils.RestBasePathsUtil.InvokeMessage.getMessageRest;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.camunda.utils.ProcessInfoUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ControllerTest<T> {

    protected static final String HTTP_LOCALHOST = "http://localhost:";

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected T controller;

    @Test
    @Order(1)
    public void controllerExistsTest() {
        assertThat(controller).isNotNull();
    }

    @AfterAll
    public void closeAllActiveProcesses() {
        ProcessInfoUtil.getActiveProcesses()
                .forEach(p -> restTemplate.getForObject(HTTP_LOCALHOST + port + getMessageRest(p), String.class));
    }
}
