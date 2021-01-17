package com.example.camunda.controllers;

import static j2html.TagCreator.a;
import static j2html.TagCreator.each;
import static j2html.TagCreator.html;
import static j2html.TagCreator.li;
import static j2html.TagCreator.ul;

import com.example.camunda.CamundaProcessApplication;
import java.util.List;
import java.util.stream.Collectors;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
public class ProcessInfoController {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessInfoController.class);

    @GetMapping("/info")
    public String index() {
        LOG.info("[INFO] show info about active processes.");
        final ProcessEngine processEngine = BpmPlatform.getDefaultProcessEngine();
        final RuntimeService runtimeService = processEngine.getRuntimeService();
        final RepositoryService repositoryService = processEngine.getRepositoryService();
        final ProcessDefinition myProcessDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionName(CamundaProcessApplication.PROCESS_NAME).latestVersion().singleResult();

        final List<String> processInstances = runtimeService
                .createProcessInstanceQuery()
                .processDefinitionId(myProcessDefinition.getId())
                .active()
                .list()
                .stream()
                .map(m -> m.getProcessInstanceId())
                .collect(Collectors.toList());

        if (processInstances.isEmpty()) {
            return html("There is not active process instance.").renderFormatted();
        } else {
            return html(a("Active instances"), ul(each(processInstances, m -> li(m)))).renderFormatted();
        }
    }
}
