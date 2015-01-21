package com.everis.webservice;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class SOAPClientSAAJ {

    private WSDLOperation wsdlOperation;
    
    public WSDLOperation getWsdlOperation() {
        return wsdlOperation;
    }

    public void setWsdlOperation(WSDLOperation wsdlOperation) {
        this.wsdlOperation = wsdlOperation;
    }

    public static void main(String args[]) throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        String url = "http://192.168.247.1:8081/CentrosBBVAWS/CentrosBBVAWebService?wsdl";
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

        // print SOAP Response
        System.out.print("Response SOAP Message:");
        soapResponse.writeTo(System.out);

        soapConnection.close();
    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://www.bbva.com.pe/CentrosBBVAWebService/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("saaj", serverURI);
        envelope.addNamespaceDeclaration("", "");

        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:example="http://ws.cdyne.com/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <example:VerifyEmail>
                    <example:email>mutantninja@gmail.com</example:email>
                    <example:LicenseKey>123</example:LicenseKey>
                </example:VerifyEmail>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
         */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("listarOficinaTerritorioSuprareaRequest", "saaj");
        soapBodyElem.addChildElement("term").addTextNode("%");
//        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("email", "example");
//        soapBodyElem1.addTextNode("mutantninja@gmail.com");
//        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("LicenseKey", "example");
//        soapBodyElem2.addTextNode("123");

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "listarOficinaTerritorioSuprarea");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println();

        
        return soapMessage;
    }

}