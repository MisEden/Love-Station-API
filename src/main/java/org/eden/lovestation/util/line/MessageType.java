package org.eden.lovestation.util.line;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public enum MessageType implements Serializable {
    @JsonProperty("text")
    TEXT
}
