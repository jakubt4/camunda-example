package com.example.camunda.controllers;

import static com.example.camunda.utils.RestBasePathsUtil.Main.getWaitRest;
import static com.example.camunda.utils.RestBasePathsUtil.ProcessInfo.PROCESS_INFO;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public final class ProcessInfoControllerTest extends ControllerTest<ProcessInfoController> {

    @Test
    @Order(1)
    public void infoGetWithoutActiveProcessTest() {
        assertThat(restTemplate.getForObject(HTTP_LOCALHOST + port + PROCESS_INFO,
                String.class))
                .contains("There is not active process instance.");
    }

    @Test
    @Order(2)
    public void infoGetWithActiveProcessTest() {
        restTemplate.getForObject(HTTP_LOCALHOST + port + getWaitRest(Boolean.TRUE), String.class);
        assertThat(restTemplate.getForObject(HTTP_LOCALHOST + port + PROCESS_INFO,
                String.class))
                .contains("Active instances");
    }
}