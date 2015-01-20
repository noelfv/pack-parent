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

    private static final Logger LOGGER = Logger.getLogger(DOMReader.class);
    private List<DOMFilter> filters;

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
        Node attr;
        for (int j = 0; j < node.getChildNodes().getLength(); j++) {
            item = node.getChildNodes().item(j);
            if (item.getNodeType() == Node.ELEMENT_NODE && item.getChildNodes().getLength() > 0) {
                obtenerElementos(item, path + item.getNodeName() + "/");
            } else if (item.getNodeType() == Node.ELEMENT_NODE) {
                attr = item.getAttributes().getNamedItem("name");
                LOGGER.info("path: " + path + item.getNodeName() + "/" + (attr == null ? "" : attr.getNodeValue()));
                read((Element) item);
            }
        }
    }

    public void read(Element element) throws DOMReaderException {
        for (DOMFilter filter : filters) {
            filter.read(element);
        }
    }

    public void read(InputStream io, String path) throws DOMReaderException {
        Document doc = obtenerDocumento(io);
        obtenerElementos(doc.getFirstChild(), "/");
    }
}
