package com.everis.webservice;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.log4j.Logger;

import com.sun.xml.messaging.saaj.util.ByteOutputStream;

public class SOAPClientSAAJ {

    private static final Logger LOGGER = Logger.getLogger(SOAPClientSAAJ.class);
    private WSDLResource wsdlResource;
    private String namespaceDeclaration;
    
    public SOAPClientSAAJ() throws UnsupportedOperationException, SOAPException {
        super();
    }
    
    public SOAPClientSAAJ(WSDLResource wsdlResource) throws UnsupportedOperationException, SOAPException {
        this();
        this.wsdlResource = wsdlResource;
    }

    public void setNamespaceDeclaration(String targetNamespace) {
        namespaceDeclaration = targetNamespace;
    }
    
    public ByteArrayOutputStream executeOperation(String operation, Map<String, String> values) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WSDLOperation wsdlOperation = wsdlResource.getOperations().get(operation);
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(wsdlOperation), wsdlResource.getUrl());
            soapResponse.writeTo(bos);
            soapConnection.close();
            
            LOGGER.info("Request byte size: " + bos.size());
        } catch(SOAPException e) {
            LOGGER.error("Error al generar la peticion SOAP", e);
        } catch (Exception e) {
            LOGGER.error("Error no experado", e);
        }
        
        return bos;
    }
    
    private void setNamespaceDeclaration(SOAPEnvelope soapEnvelope) throws SOAPException {
        soapEnvelope.addNamespaceDeclaration("saaj", namespaceDeclaration);
        soapEnvelope.addNamespaceDeclaration("", "");
    }
    
    private void setBody(WSDLOperation wsdlOperation, SOAPEnvelope soapEnvelope) throws SOAPException {
        SOAPBody soapBody = soapEnvelope.getBody();
        WSDLElement input = wsdlOperation.getInput().getElement();
        LOGGER.info(input.getName());
    }
    
    private SOAPMessage createSOAPRequest(WSDLOperation wsdlOperation) throws Exception {
        String serverURI = wsdlResource.getTargetNamespace();
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + wsdlOperation.getName());
        
        setNamespaceDeclaration(soapEnvelope);
        setBody(wsdlOperation, soapEnvelope);
        
        soapMessage.saveChanges();
        
        if(LOGGER.isInfoEnabled()) {
            ByteOutputStream bos = new ByteOutputStream();
            soapMessage.writeTo(bos);
            LOGGER.info("Request SOAP Message:\n" + new String(bos.getBytes()));
        }
        
        return soapMessage;     
    }
}