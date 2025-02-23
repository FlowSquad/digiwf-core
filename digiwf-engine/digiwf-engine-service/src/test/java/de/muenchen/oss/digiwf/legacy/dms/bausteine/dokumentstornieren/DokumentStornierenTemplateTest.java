/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.bausteine.dokumentstornieren;

import de.muenchen.oss.digiwf.engine.basis.process.DigitalWFFunctions;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.service.DmsService;
import de.muenchen.oss.digiwf.legacy.dms.muc.process.canceldokument.CancelDokumentDelegate;
import de.muenchen.oss.digiwf.legacy.mailing.process.TestSendMailDelegate;
import de.muenchen.oss.digiwf.shared.properties.DigitalWFProperties;
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
import org.mockito.junit.jupiter.MockitoExtension;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Deployment(resources = { "bausteine/dms/dokumentstornieren/DokumentStornierenV01.bpmn",
        "prozesse/feature/unittests/dms/dokumentstornieren/Feature_DokumentStornieren.bpmn" })
@ExtendWith(MockitoExtension.class)
public class DokumentStornierenTemplateTest {

    public static final String TEMPLATE_KEY = "FeatureDokumentStornieren";
    public static final String VAR_DOKUMENTCOO = "FormField_DokumentCOO";
    public static final String VAR_DMS_BENUTZER = "FormField_DMSBenutzer";
    public static final String ACTIVITY_DOKUMENT_STORNIEREN = "Activity_DokumentStornieren";
    public static final String END_EVENT_DOKUMENT_STORNIERT = "EndEvent_DokumentStorniert";
    public static final String TASK_DOKUMENT_STORNIEREN = "Task_DokumentStornieren";
    public static final String VAR_DMS_TASK_TITLE_MANUELL = "dms_task_title_manuell";
    public static final String TASK_DOKUMENT_MANUELL_STORNIEREN = "Task_DokumentManuellStornieren";

    @RegisterExtension
    public ProcessEngineExtension processEngineExtension = ProcessEngineExtension.builder()
        .configurationResource("camunda.cfg.xml")
        .build();

    @Mock
    private ProcessScenario processScenario;

    @Mock
    private ProcessScenario templateScenario;

    @Mock
    private DigitalWFProperties digitalWFProperties;

    @Mock
    private DmsService dmsService;

    @BeforeEach
    public void defaultScenario() throws Exception {

        Mocks.register("cancelDokumentDelegate", new CancelDokumentDelegate(this.dmsService));
        Mocks.register("sendMailDelegate", new TestSendMailDelegate());
        Mocks.register("digitalwf", new DigitalWFFunctions(this.digitalWFProperties));

        when(this.processScenario.runsCallActivity(ACTIVITY_DOKUMENT_STORNIEREN))
                .thenReturn(Scenario.use(this.templateScenario));


    }

    @Test
    public void shouldExecuteHappyPath() throws Exception {

        doNothing().when(this.dmsService).cancelDocument(any(), any());



        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, withVariables(
                        VAR_DOKUMENTCOO, "VorgangCOO",
                        VAR_DMS_BENUTZER, "dmsBenutzer"
                ))
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_DOKUMENT_STORNIEREN);
        verify(this.templateScenario).hasFinished(END_EVENT_DOKUMENT_STORNIERT);
    }

    @Test
    public void shouldExecuteWithError() throws Exception {

        doThrow(RuntimeException.class).when(this.dmsService).cancelDocument(any(), any());
        when(this.digitalWFProperties.getFrontendUrl()).thenReturn("myUrl");

        when(this.templateScenario.waitsAtUserTask(TASK_DOKUMENT_MANUELL_STORNIEREN))
            .thenReturn(TaskDelegate::complete);

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, withVariables(
                        VAR_DOKUMENTCOO, "VorgangCOO",
                        VAR_DMS_BENUTZER, "dmsBenutzer",
                        VAR_DMS_TASK_TITLE_MANUELL, "Mein Vorgang"
                ))
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_DOKUMENT_MANUELL_STORNIEREN);

        verify(this.templateScenario).hasFinished(END_EVENT_DOKUMENT_STORNIERT);
    }
}
