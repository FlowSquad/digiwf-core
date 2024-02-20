/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.bausteine.schriftstueckupdaten;

import de.muenchen.oss.digiwf.engine.basis.process.DigitalWFFunctions;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.service.DmsService;
import de.muenchen.oss.digiwf.legacy.dms.muc.external.transport.DMSException;
import de.muenchen.oss.digiwf.legacy.dms.muc.external.transport.DMSStatusCode;
import de.muenchen.oss.digiwf.legacy.dms.muc.process.updateschriftstueck.UpdateSchriftstueckDelegate;
import de.muenchen.oss.digiwf.legacy.dms.shared.S3Resolver;
import de.muenchen.oss.digiwf.legacy.document.domain.DocumentService;
import de.muenchen.oss.digiwf.legacy.mailing.process.TestSendMailDelegate;
import de.muenchen.oss.digiwf.legacy.user.process.UserFunctions;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.delegate.TaskDelegate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
import static org.mockito.Mockito.*;

@Deployment(resources = {"bausteine/dms/schriftstueckupdaten/SchriftstueckUpdatenV01.bpmn",
        "bausteine/dms/schriftstueckupdaten/feature/Feature_SchriftstueckeUpdaten.bpmn"})
@ExtendWith(MockitoExtension.class)
public class SchriftstueckUpdatenTemplateTest {

    public static final String TEMPLATE_KEY = "FeatureSchriftstueckeUpdaten";
    public static final String STARTER_OF_INSTANCE = "starterOfInstance";
    public static final String TASK_SCHRIFTSTUECK_UPDATEN = "Task_SchriftstueckUpdaten";
    public static final String TASK_SACHBEARBEITUNG_AUSWAEHLEN = "Task_SachbearbeitungAuswaehlen";
    public static final String FORM_FIELD_NEUE_SACHBEARBEITUNG = "FormField_Neue_Sachbearbeitung";
    public static final String FORM_FIELD_SCHRIFTSTUECK_NAME1 = "FormField_SchriftstueckName";
    public static final String FORM_FIELD_SCHRIFTSTUECK_COO = "FormField_SchriftstueckCOO";
    public static final String END_EVENT_BEENDET = "EndEvent_Beendet";
    public static final String EVENT_ERROR_GESPERRT = "Event_ErrorGesperrt";
    public static final String EVENT_ERROR_FEHLENDE_BERECHTIGUNG = "Event_Error_FehlendeBerechtigung";
    public static final String TASK_SACHBEARBEITUNG_AUSWAEHLEN1 = "Task_SachbearbeitungAuswaehlen";
    public static final String TASK_SPERRE_AUFHEBEN = "Task_SperreAufheben";

    @RegisterExtension
    public static ProcessEngineExtension processEngineExtension = ProcessEngineExtension.builder()
        .configurationResource("camunda.cfg.xml")
        .build();
    @Mock
    private ProcessScenario processScenario;

    @Mock
    private ProcessScenario templateScenario;

    @Mock
    private DmsService dmsService;

    @Mock
    private DocumentService documentService;

    @Mock
    private UserFunctions userFunctions;

    @Mock
    private S3Resolver s3Resolver;

    @Mock
    private DigitalWFFunctions digitalWF;

    @BeforeEach
    public void defaultScenario() throws Exception {

        Mocks.register("updateSchriftstueckDelegate", new UpdateSchriftstueckDelegate(this.dmsService, this.documentService, this.s3Resolver));
        when(this.documentService.createDocument(anyString(), anyString())).thenReturn("Document".getBytes());

        Mocks.register("sendMailDelegate", new TestSendMailDelegate());

        Mocks.register("digitalwf", this.digitalWF);

        //when(this.digitalWF.urlGruppenaufgaben()).thenReturn("myurl");

        Mocks.register("user", this.userFunctions);

        when(this.userFunctions.email(any())).thenReturn("externer.dl.horn@muenchen.de");

        when(this.processScenario.runsCallActivity(TASK_SCHRIFTSTUECK_UPDATEN))
                .thenReturn(Scenario.use(this.templateScenario));

    }

    @Test
    public void shouldExecuteHappyPath() throws Exception {
        doNothing().when(this.dmsService).updateSchriftstueck(any(), any());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, this.getVariableMap())
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_SCHRIFTSTUECK_UPDATEN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithGesperrt() throws Exception {

        when(this.templateScenario.waitsAtUserTask(TASK_SPERRE_AUFHEBEN))
                .thenReturn(TaskDelegate::complete);


        doThrow(new DMSException(DMSStatusCode.OBJEKT_GESPERRT, "Gesperrt")).doNothing().when(this.dmsService).updateSchriftstueck(any(), any());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, this.getVariableMap())
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_SCHRIFTSTUECK_UPDATEN);
        verify(this.templateScenario).hasCompleted(EVENT_ERROR_GESPERRT);
        verify(this.templateScenario).hasCompleted(TASK_SPERRE_AUFHEBEN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithFehlendeBerechtigung() throws Exception {

        when(this.templateScenario.waitsAtUserTask(TASK_SACHBEARBEITUNG_AUSWAEHLEN))
            .thenReturn(task -> task.complete(withVariables(FORM_FIELD_NEUE_SACHBEARBEITUNG, "neueSachbearbeitung")));


        doThrow(new DMSException(DMSStatusCode.FEHLENDE_BERECHTIGUNG, "Fehlende Berechtigung")).doNothing().when(this.dmsService)
                .updateSchriftstueck(any(), any());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, this.getVariableMap())
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_SCHRIFTSTUECK_UPDATEN);
        verify(this.templateScenario).hasCompleted(EVENT_ERROR_FEHLENDE_BERECHTIGUNG);
        verify(this.templateScenario).hasCompleted(TASK_SACHBEARBEITUNG_AUSWAEHLEN1);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    private Map<String, Object> getVariableMap() {
        return withVariables(
                FORM_FIELD_SCHRIFTSTUECK_NAME1, "Schriftstück Name",
                STARTER_OF_INSTANCE, "startUser",
                FORM_FIELD_SCHRIFTSTUECK_COO, "SchriftstueckCOO"
        );
    }

}
