package io.muenchendigital.digiwf.message.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageSuccess {
    private boolean success;
    private String message;
}
