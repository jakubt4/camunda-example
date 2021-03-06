package com.example.camunda.controllers;

import static com.example.camunda.utils.J2HtmlUtil.createhref;
import static com.example.camunda.utils.RestBasePathsUtil.InvokeMessage.getMessageRest;
import static com.example.camunda.utils.RestBasePathsUtil.Main.MAIN;
import static com.example.camunda.utils.RestBasePathsUtil.Main.getWaitRest;
import static j2html.TagCreator.a;
import static j2html.TagCreator.br;
import static j2html.TagCreator.html;

import com.example.camunda.CamundaProcessApplication;
import com.example.camunda.services.InitService;
import java.util.concurrent.atomic.AtomicInteger;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
    private static final AtomicInteger SESSION_ID = new AtomicInteger(0);

    @Autowired
    private InitService init;

    @GetMapping(value = "/main", produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        LOG.info("[MAIN-{}] init main page", SESSION_ID.incrementAndGet());
        return html(
                a("SESSION-" + SESSION_ID.get()),
                br(),
                createhref("Main", MAIN),
                br(),
                createhref("With WAIT", getWaitRest(Boolean.TRUE)),
                br(),
                createhref("Without WAIT", getWaitRest(Boolean.FALSE)))
                .renderFormatted();
    }

    @GetMapping(value = "/wait/{isWaiting}", produces = MediaType.TEXT_HTML_VALUE)
    public String startProcess(@PathVariable("isWaiting") boolean isWaiting) {
        LOG.info("[START PROCESS] Starting {} process {} waiting",
                CamundaProcessApplication.PROCESS_NAME,
                isWaiting ? "with" : "without");

        final ProcessInstance pi = init.initProcess(isWaiting);

        if (isWaiting) {
            LOG.info("[MESSAGE-FOR-PROCESS-ID-{}] Waiting for message..", pi.getProcessInstanceId());
            return html(
                    a("PROCESS-ID:" + pi.getProcessInstanceId()),
                    br(),
                    a("Waiting for message. Invoke message by"),
                    createhref("THIS", getMessageRest(pi.getProcessInstanceId())))
                    .renderFormatted();
        }

        LOG.info("[PROCESS-ID:{}] Finished.", pi.getProcessInstanceId());
        return html(
                a("PROCESS-ID:" + pi.getProcessInstanceId()),
                br(),
                a("Finished process instance id: " + pi.getProcessInstanceId()),
                createhref("Back to Main", MAIN))
                .renderFormatted();
    }
}