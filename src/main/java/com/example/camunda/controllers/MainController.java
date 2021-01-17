package com.example.camunda.controllers;

import static com.example.camunda.utils.J2HtmlUtil.createhref;
import static com.example.camunda.utils.RestBasePathsUtil.InvokeMessage.getMessageRest;
import static com.example.camunda.utils.RestBasePathsUtil.Main.MAIN;
import static com.example.camunda.utils.RestBasePathsUtil.Main.getWaitRest;
import static j2html.TagCreator.a;
import static j2html.TagCreator.br;
import static j2html.TagCreator.html;

import com.example.camunda.CamundaProcessApplication;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("/main")
    public String index() {
        LOG.info("[MAIN] init main page");
        return html(
                createhref("Main", MAIN),
                br(),
                createhref("With WAIT", getWaitRest("true")),
                br(),
                createhref("Without WAIT", getWaitRest("false")))
                .renderFormatted();
    }

    @GetMapping("/wait/{isWaiting}")
    public String startProcess(@PathVariable("isWaiting") boolean isWaiting) {
        LOG.info("[START PROCESS] Starting {} process {} waiting",
                CamundaProcessApplication.PROCESS_NAME,
                isWaiting ? "with" : "without");

        final Map<String, Object> params = new HashMap<>();
        params.put("wait", isWaiting);

        final ProcessInstance pi = runtimeService.startProcessInstanceByKey(CamundaProcessApplication.PROCESS_NAME,
                params);

        if (isWaiting) {
            LOG.info("[MESSAGE] Waiting for message..");
            return html(
                    a("Waiting for message. Invoke message by"),
                    createhref("THIS", getMessageRest(pi.getProcessInstanceId())))
                    .renderFormatted();
        }

        LOG.info("[PROCESS] Finished.");
        return html(
                a("Finished process instance id: " + pi.getProcessInstanceId()),
                createhref("Back to Main", MAIN))
                .renderFormatted();
    }
}