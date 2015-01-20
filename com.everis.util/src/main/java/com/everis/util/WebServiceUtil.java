package com.everis.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.everis.webservice.Message;
import com.everis.webservice.Operation;
import com.everis.webservice.WSDLResource;

public class WebServiceUtil {

    private static final Logger LOG = Logger.getLogger(WebServiceUtilTest.class);
    
    public Document getDocument(InputStream io) {
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(io);
        } catch (SAXException e) {
            LOG.error("", e);
        } catch (IOException e) {
            LOG.error("", e);
        } catch (ParserConfigurationException e) {
            LOG.error("", e);
        }
        
        return doc;
    }
    
    public List<Operation> getOperations(Document doc) {
        List<Operation> operations = new ArrayList<Operation>();
        Operation operation;
        Message message;
        Element element;
        NodeList elements = ((Element) doc.getElementsByTagName("wsdl:portType").item(0)).getElementsByTagName("wsdl:operation");
        Node node;
        for (int i = 0; i < elements.getLength(); i++) {
            node = elements.item(i);
            operation = new Operation();
            operation.setName(node.getAttributes().getNamedItem("name").getNodeValue());
            
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                message = new Message();
                message.setName(element.getElementsByTagName("wsdl:input").item(0).getAttributes().getNamedItem("name").getNodeValue());
                operation.setInput(message);
                
                message = new Message();
                message.setName(element.getElementsByTagName("wsdl:output").item(0).getAttributes().getNamedItem("name").getNodeValue());
                operation.setOutput(message);
            }
            
            operations.add(operation);
        }
        
        return operations;
    }

    public static WSDLResource readWSDL(String wsdl) {
        WSDLResource wsdlResource = new WSDLResource();
        
        try {
            URL url = new URL(wsdl);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            LOG.info("\n" + IOUtils.toString(in));
        } catch(MalformedURLException e) {
            LOG.error(wsdl, e);
        } catch (IOException e) {
            LOG.error(wsdl, e);
        }
        
        return wsdlResource;
    }
}
