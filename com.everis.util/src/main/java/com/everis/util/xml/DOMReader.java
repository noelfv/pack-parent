package com.everis.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.everis.util.xml.filter.DOMFilter;

public class DOMReader implements DOMFilter {

    private static String pathSeparator = "$";
    private static final Logger LOGGER = Logger.getLogger(DOMReader.class);
    private List<DOMFilter> filters;

    public static String getPathSeparator() {
        return pathSeparator;
    }
    
    public static void setPathSeparator(String pathSeparator) {
        DOMReader.pathSeparator = pathSeparator;
    }

    public DOMReader(List<DOMFilter> filters) {
        super();
        this.filters = filters;
    }

    public DOMReader(DOMFilter filter) {
        super();
        this.filters = new ArrayList<DOMFilter>();
        this.filters.add(filter);
    }
    
    public List<DOMFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<DOMFilter> filters) {
        this.filters = filters;
    }

    private Document obtenerDocumento(InputStream io) throws DOMReaderException {
        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(io);
        } catch (SAXException e) {
            throw new DOMReaderException("", e);
        } catch (IOException e) {
            throw new DOMReaderException("", e);
        } catch (ParserConfigurationException e) {
            throw new DOMReaderException("", e);
        }

        return document;
    }

    private void obtenerElementos(Node node, String path) throws DOMReaderException {
        Node item;
        short type;
        for (int j = 0; j < node.getChildNodes().getLength(); j++) {
            item = node.getChildNodes().item(j);
            type = (item.getChildNodes().getLength() > 0 ? DOMFilter.NODE : DOMFilter.ELEMENT);
            LOGGER.info(path + item.getNodeName() + "$%" + type);
            
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                read((Element) item, path + item.getNodeName(), type);            
                if (type == DOMFilter.NODE) {
                    obtenerElementos(item, path + item.getNodeName() + pathSeparator);
                }
            }
        }
    }

    public void read(Element element, String path, short nivel) throws DOMReaderException {
        for (DOMFilter filter : filters) {
            filter.read(element, path, nivel);
        }
    }
    
    public void read(InputStream io, String path) throws DOMReaderException {
        Document doc = obtenerDocumento(io);
        obtenerElementos(doc.getFirstChild(), path);
    }
}
