package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.in;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CamundaClientConfiguration {
    public final static String TYPE_NAME = "app_type_name";
    public final static String TOPIC_NAME = "app_topic_name";
    public final static String INTEGRATION_NAME = "app_integration_name";

    private final List<String> filteredVariables;

    public List<String> getFilters() {
        final List<String> allFilters = new ArrayList<>();
        allFilters.addAll(this.filteredVariables);
        allFilters.addAll(List.of(TOPIC_NAME, TYPE_NAME, INTEGRATION_NAME));
        return allFilters;
    }
}
