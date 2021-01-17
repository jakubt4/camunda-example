package com.example.camunda.controllers;

import static j2html.TagCreator.a;
import static j2html.TagCreator.each;
import static j2html.TagCreator.html;
import static j2html.TagCreator.li;
import static j2html.TagCreator.ul;

import com.example.camunda.utils.ProcessInfoUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
public class ProcessInfoController {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessInfoController.class);

    @GetMapping(value = "/info", produces = MediaType.TEXT_HTML_VALUE)
    public String info() {
        LOG.info("[INFO] show info about active processes.");

        final List<String> activeProcesses = ProcessInfoUtil.getActiveProcesses();
        if (activeProcesses.isEmpty()) {
            return html("There is not active process instance.").renderFormatted();
        } else {
            return html(a("Active instances"), ul(each(activeProcesses, m -> li(m)))).renderFormatted();
        }
    }
}
