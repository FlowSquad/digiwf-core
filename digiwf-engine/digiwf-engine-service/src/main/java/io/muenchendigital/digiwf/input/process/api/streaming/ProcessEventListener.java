package io.muenchendigital.digiwf.input.process.api.streaming;

import io.muenchendigital.digiwf.input.process.api.transport.StartInstanceTOV01;
import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import io.muenchendigital.digiwf.service.definition.domain.facade.ServiceDefinitionFacade;
import io.muenchendigital.digiwf.asyncapi.docs.annotations.DocumentAsyncAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

/**
 * Generic Listener to start processes.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessEventListener {

    public static final String DEFAULT_USER = "digiwf";

    private final ServiceDefinitionFacade serviceDefinitionFacade;
    private final UserService userService;

    @DocumentAsyncAPI(payload = StartInstanceTOV01.class, functionRouter = true, typeHeader = "startProcessV01")
    @Bean
    public Consumer<Message<StartInstanceTOV01>> startProcessV01() {
        return startInstance -> {
            log.info("Received process start {}", startInstance.getPayload());
            this.serviceDefinitionFacade.startInstance(
                    startInstance.getPayload().getKey(),
                    startInstance.getPayload().getBusinessKey(),
                    startInstance.getPayload().getFileContext(),
                    startInstance.getPayload().getData(),
                    StringUtils.isBlank(startInstance.getPayload().getStarterOfInstance()) ? DEFAULT_USER : startInstance.getPayload().getStarterOfInstance(),
                    getUserGroups(startInstance.getPayload().getStarterOfInstance()));
        };
    }

    private List<String> getUserGroups(String starterOfInstance) {
        List<String> groups = List.of();
        if (StringUtils.isNotBlank(starterOfInstance) && userService.getUserOrNull(starterOfInstance).isPresent()){
            groups = userService.getGroups(starterOfInstance);
        }
        return groups;
    }
}
