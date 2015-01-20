package com.everis.util.xml.filter;

import org.w3c.dom.Element;

import com.everis.util.xml.DOMReaderException;

public interface DOMFilter {

    void read(Element element) throws DOMReaderException;
}
