package com.everis.mail;

public class Message {

    private MessageHeader header;
    private MessageBody body;

    public Message() {
        header = new MessageHeader();
        body = new MessageBody();
    }

    public MessageHeader getHeader() {
        return header;
    }

    public void setHeader(MessageHeader header) {
        this.header = header;
    }

    public MessageBody getBody() {
        return body;
    }

    public void setBody(MessageBody body) {
        this.body = body;
    }
}
