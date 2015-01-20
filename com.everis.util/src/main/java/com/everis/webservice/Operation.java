package com.everis.webservice;

public class Operation {

    private String name;
    private Message input;
    private Message output;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getInput() {
        return input;
    }

    public void setInput(Message input) {
        this.input = input;
    }

    public Message getOutput() {
        return output;
    }

    public void setOutput(Message output) {
        this.output = output;
    }

}
