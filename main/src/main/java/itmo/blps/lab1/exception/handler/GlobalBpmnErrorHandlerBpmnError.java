package itmo.blps.lab1.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GlobalBpmnErrorHandlerBpmnError {
    @EventListener
    public void handleBpmnError(Exception e) {
        if (e instanceof BpmnError bpmnError) {
            log.warn("Handled BPMN Error: [{}] {}", bpmnError.getErrorCode(), bpmnError.getMessage());
        }
    }
}
