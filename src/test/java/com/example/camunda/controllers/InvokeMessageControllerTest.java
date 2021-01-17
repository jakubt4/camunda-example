package com.example.camunda.controllers;

import static com.example.camunda.utils.RestBasePathsUtil.InvokeMessage.getMessageRest;
import static com.example.camunda.utils.RestBasePathsUtil.Main.getWaitRest;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.camunda.utils.ProcessInfoUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class InvokeMessageControllerTest extends ControllerTest<InvokeMessageController> {

    @Test
    @Order(1)
    public void invokeMessageProcessNotExistTest() {
        final String isWaitingHTML = restTemplate.getForObject(HTTP_LOCALHOST + port + getWaitRest(Boolean.TRUE),
                String.class);

        assertThat(ProcessInfoUtil.getActiveProcesses().size()).isEqualTo(1);

        final Pattern pattern = Pattern.compile("process/.*/message/");
        final Matcher matcher = pattern.matcher(isWaitingHTML);
        if (matcher.find()) {
            final String result = matcher.group().replace("process/", "").replace("/message/", "");
            assertThat(restTemplate.getForObject(HTTP_LOCALHOST + port + getMessageRest(result), String.class))
                    .contains("Result:", "Back to Main");
            assertThat(ProcessInfoUtil.getActiveProcesses().size()).isEqualTo(0);
        } else {
            throw new AssertionError();
        }
    }
}
