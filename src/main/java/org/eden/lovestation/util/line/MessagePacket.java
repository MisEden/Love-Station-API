package org.eden.lovestation.util.line;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MessagePacket {
    private String to;
    private List<Message> messages;
}
