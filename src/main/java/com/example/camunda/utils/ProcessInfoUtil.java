package com.example.camunda.utils;

import com.example.camunda.CamundaProcessApplication;
import java.util.List;
import java.util.stream.Collectors;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;

public final class ProcessInfoUtil {

    private static final ProcessEngine PROCESS_ENIGNE = BpmPlatform.getDefaultProcessEngine();
    private static final RuntimeService RUNTIME_SERVICE = PROCESS_ENIGNE.getRuntimeService();
    private static final RepositoryService REPOSITORY_SERVICE = PROCESS_ENIGNE.getRepositoryService();

    private ProcessInfoUtil() {
        throw new UnsupportedOperationException("Util class");
    }

    public static List<String> getActiveProcesses() {
        final ProcessDefinition myProcessDefinition = REPOSITORY_SERVICE
                .createProcessDefinitionQuery()
                .processDefinitionName(CamundaProcessApplication.PROCESS_NAME)
                .latestVersion()
                .singleResult();

        return getActualProcesses(RUNTIME_SERVICE,myProcessDefinition)
                .active()
                .list()
                .stream()
                .map(m -> m.getProcessInstanceId())
                .collect(Collectors.toList());
    }

    private static ProcessInstanceQuery getActualProcesses(
            final RuntimeService runtimeService,
            final ProcessDefinition myProcessDefinition) {
        return runtimeService.createProcessInstanceQuery().processDefinitionId(myProcessDefinition.getId());
    }
}
