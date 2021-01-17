package com.example.camunda.controllers;

import static com.example.camunda.utils.J2HtmlUtil.createhref;
import static com.example.camunda.utils.RestBasePathsUtil.Main.MAIN;
import static j2html.TagCreator.a;
import static j2html.TagCreator.br;
import static j2html.TagCreator.html;

import com.example.camunda.services.InvokeMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoke")
public class InvokeMessageController {

    private static final Logger LOG = LoggerFactory.getLogger(InvokeMessageController.class);

    @Autowired
    private InvokeMessageService invoker;

    @GetMapping(value = "/process/{processId}/message/{message}", produces = MediaType.TEXT_HTML_VALUE)
    public String startProcess(
            @PathVariable("processId") String processId,
            @PathVariable("message") String message) {
        LOG.info("[INVOKE MESSAGE] Invoke message {} with process id {}.", message, processId);

        return html(
                a("Result: " + invoker.invokeMessageForProcess(processId, message).getResultType().toString()),
                br(),
                createhref("Back to Main", MAIN))
                .renderFormatted();
    }

}
