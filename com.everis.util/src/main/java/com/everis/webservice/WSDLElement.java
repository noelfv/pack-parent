package com.everis.webservice;

import java.util.ArrayList;
import java.util.List;

public class WSDLElement {

    private String name;
    private String type;
    private List<WSDLElement> attributes; 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<WSDLElement> getAttributes() {
        if(attributes == null) {
            attributes = new ArrayList<WSDLElement>();
        }
        return attributes;
    }

    public void setAttributes(List<WSDLElement> attributes) {
        this.attributes = attributes;
    }

}
