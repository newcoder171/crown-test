package com.crown.test.core.data;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class Response {
    private Object payload;
    private HttpStatus status = HttpStatus.OK;
    private List<Message> messages = new ArrayList<>();

    public Response(Object payload) {
        this.payload = payload;
    }

    public Response(Object payload, HttpStatus status) {
        this.payload = payload;
        this.status = status;
    }

    public Response error(String msg) {
        Message message = new Message(msg, MessageType.ERROR);
        messages.add(message);
        return this;
    }

    public Response success(String msg) {
        Message message = new Message(msg, MessageType.SUCCESS);
        messages.add(message);
        return this;
    }

    public static Response of(Object payload) {
        return new Response(payload);
    }
    public static Response of(Object payload, HttpStatus status) {
        return new Response(payload, status);
    }
}
