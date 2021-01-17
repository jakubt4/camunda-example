package com.example.camunda.controllers;

import static com.example.camunda.utils.RestBasePathsUtil.Main.MAIN;
import static com.example.camunda.utils.RestBasePathsUtil.Main.getWaitRest;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.camunda.utils.ProcessInfoUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class MainControllerTest extends ControllerTest<MainController> {

    @Test
    @Order(1)
    public void mainIndexTest() {
        assertThat(restTemplate.getForObject(HTTP_LOCALHOST + port + MAIN, String.class))
                .contains("Main", "With WAIT", "Without WAIT");
    }


    @Test
    @Order(2)
    public void mainIsNotWaitingTest() {
        assertWait(Boolean.FALSE, "Finished process instance id", 0);
    }


    @Test
    @Order(3)
    public void mainIsWaitingTest() {
        assertWait(Boolean.TRUE, "Waiting for message. Invoke message by", 1);
    }

    private void assertWait(Boolean isWaiting, String contains, Integer numActiveProcesses) {
        assertThat(restTemplate.getForObject(HTTP_LOCALHOST + port + getWaitRest(isWaiting), String.class))
                .contains(contains);
        assertThat(ProcessInfoUtil.getActiveProcesses().size()).isEqualTo(numActiveProcesses);
    }
}
