package io.muenchendigital.digiwf.integration.adapter.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DigiwfEvent<T> {
    private String type;
    private String digiwfProcessInstanceId;
    private String digiwfMessageName;
    private T data;
}
