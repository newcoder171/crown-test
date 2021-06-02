package com.crown.test.core.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    protected String msg;
    @Transient
    protected MessageType msgType = MessageType.INFO;
}
