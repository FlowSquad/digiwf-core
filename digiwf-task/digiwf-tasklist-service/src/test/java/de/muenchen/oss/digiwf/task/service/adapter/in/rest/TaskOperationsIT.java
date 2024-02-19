package de.muenchen.oss.digiwf.task.service.adapter.in.rest;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.common.collect.Sets;
import de.muenchen.oss.digiwf.task.service.TaskListApplication;
import de.muenchen.oss.digiwf.task.service.adapter.out.user.MockUserGroupResolverAdapter;
import de.muenchen.oss.digiwf.task.service.application.port.out.engine.TaskCommandPort;
import de.muenchen.oss.digiwf.task.service.infra.metrics.CollectorRegistryMockingConfiguration;
import de.muenchen.oss.digiwf.task.service.infra.security.TestUser;
import de.muenchen.oss.digiwf.task.service.infra.security.WithKeycloakUser;
import io.holunda.polyflow.view.Task;
import io.holunda.polyflow.view.jpa.JpaPolyflowViewTaskService;
import io.holunda.polyflow.view.query.task.AllTasksQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static de.muenchen.oss.digiwf.task.service.application.usecase.TestFixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test sending JSON request to the API and checking the correct invocation and
 * mapping to correct HTTP status.
 */
@SpringBootTest(classes = TaskListApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"itest", "embedded-kafka", "no-security"})
@AutoConfigureMockMvc
@EmbeddedKafka(
    partitions = 1,
    topics = {"${polyflow.axon.kafka.topic-tasks}", "${polyflow.axon.kafka.topic-data-entries}"}
)
@Slf4j
@WireMockTest(httpPort = 7080)
@DirtiesContext
@ContextConfiguration(classes = {CollectorRegistryMockingConfiguration.class})
public class TaskOperationsIT {


    private final Instant followUpDate = Instant.now().plus(2, ChronoUnit.DAYS);

    private final Task[] tasks = {
        // user id
        generateTask("task_0", Sets.newHashSet(), Sets.newHashSet(), TestUser.USER_ID, this.followUpDate, true),
        // candidate group
        generateTask("task_1", Sets.newHashSet(), Sets.newHashSet(MockUserGroupResolverAdapter.PRIMARY_USERGROUP, "ANOTHER"), "OTHER", null, false),
        // candidate user -> This is a special case, we don't expect candidate user assignment
        generateTask("task_2", Sets.newHashSet(TestUser.USER_ID), Sets.newHashSet(), "OTHER", null),
        // some white noise
        generateTask("task_3", Sets.newHashSet(), Sets.newHashSet(), "OTHER", null),
        generateTask("task_4", Sets.newHashSet(), Sets.newHashSet(MockUserGroupResolverAdapter.PRIMARY_USERGROUP), null, null),
    };

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaPolyflowViewTaskService service;

    @MockBean
    private TaskCommandPort taskCommandPort;

    @BeforeEach
    public void produce_task_events() {
        Arrays.stream(this.tasks).forEach(t -> this.service.on(createEvent(t), MetaData.emptyInstance()));
        await().untilAsserted(
            () -> {
                final var count = this.service.query(new AllTasksQuery()).getTotalElementCount();
                assertThat(count).isEqualTo(this.tasks.length);
            }
        );
    }

    @AfterEach
    public void clean_tasks() {
        Arrays.stream(this.tasks).forEach(t -> this.service.on(deleteEvent(t), MetaData.emptyInstance()));
    }


    @Test
    @WithKeycloakUser
    public void retrieve_task_with_details() throws Exception {
        WireMock.givenThat(
            WireMock.get(WireMock.urlEqualTo("/rest/jsonschema/schema-1"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getJsonFromFile("json-schema-1.json"))
                )
        );

        mockMvc
            .perform(
                get(RestConstants.BASE_PATH + "/tasks/id/task_0")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", equalTo("task_0")))
            .andExpect(jsonPath("$.schemaRef", equalTo("schema-1")))
        ;
    }

    @Test
    @WithKeycloakUser
    public void retrieve_task_with_schema() throws Exception {

        WireMock.givenThat(
            WireMock.get(WireMock.urlEqualTo("/rest/jsonschema/schema-1"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getJsonFromFile("json-schema-1.json"))
                )
        );

        mockMvc
            .perform(
                get(RestConstants.BASE_PATH + "/tasks/id/task_0/with-schema")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", equalTo("task_0")))
            .andExpect(jsonPath("$.schema.type", equalTo("object")))
        ;
    }

    @Test
    @WithKeycloakUser
    public void unassign_task() throws Exception {
        mockMvc
            .perform(
                post(RestConstants.BASE_PATH + "/tasks/id/task_0/unassign")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            //.andDo(print())
            .andExpect(status().isNoContent())
        ;

        verify(taskCommandPort).unassignUserTask("task_0");
    }


    @Test
    @WithKeycloakUser
    public void undefer_task() throws Exception {
        mockMvc
            .perform(
                post(RestConstants.BASE_PATH + "/tasks/id/task_0/undefer")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            //.andDo(print())
            .andExpect(status().isNoContent())
        ;

        verify(taskCommandPort).undeferUserTask("task_0");
    }


    @Test
    @WithKeycloakUser
    public void cancel_task() throws Exception {
        mockMvc
            .perform(
                post(RestConstants.BASE_PATH + "/tasks/id/task_0/cancel")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            //.andDo(print())
            .andExpect(status().isNoContent())
        ;

        verify(taskCommandPort).cancelUserTask("task_0");
    }

}
