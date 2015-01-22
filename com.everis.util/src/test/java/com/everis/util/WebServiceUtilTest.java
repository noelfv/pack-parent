package com.everis.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.soap.SOAPException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.test.context.junit4.AbstractJUnit4Test;

import com.everis.webservice.SOAPClientSAAJ;
import com.everis.webservice.WSDLResource;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class WebServiceUtilTest extends AbstractJUnit4Test {
    
    @Test
    public void readWSDL() {
        setUp();
        // WebServiceUtil.readWSDL("http://118.180.36.123/general/services/TablaGeneral/wsdl/TablaGeneral.wsdl");
        
        try {
            URL url = new URL("http://192.168.247.1:8081/CentrosBBVAWS/CentrosBBVAWebService?wsdl");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            LOGGER.info(IOUtils.toString(in));
            
            url = new URL("http://192.168.247.1:8081/CentrosBBVAWS/CentrosBBVAWebService?wsdl");
            http = (HttpURLConnection) url.openConnection();
            in = http.getInputStream();
                
            WSDLResource wsdlResource = new WSDLResource("http://192.168.247.1:8081/CentrosBBVAWS/CentrosBBVAWebService?wsdl");
            
//            prettyPrinter(wsdlFilterReader.getElements());
//            prettyPrinter(wsdlFilterReader.getMessages());
//            prettyPrinter(wsdlFilterReader.getOperations());
            
            
            String o = new JSONSerializer().deepSerialize(wsdlResource);
            LOGGER.info("\t" + o);
            JSONDeserializer<WSDLResource> jo = new JSONDeserializer<WSDLResource>();
//                    .use("elements", WSDLElement.class)
//                    .use("messages", WSDLMessage.class);
            WSDLResource t = jo.deserialize(o);
//            prettyPrinter(t.getElements());
//            prettyPrinter(t.getMessages());
//            prettyPrinter(t.getOperations());
            
            LOGGER.info("targeNamespace: " + wsdlResource.getTargetNamespace());
            LOGGER.info("Elements: " + wsdlResource.getElements().size());
            
            wsdlResource.getOperation("listarOficinaTerritorioSuprarea").getInput().getElement().setAttribute("term", "%");
            // prettyPrinter(wsdlResource.getOperation("listarOficinaTerritorioSuprarea").getInput().getElement());
            
            prettyPrinter(wsdlResource.getOperation("listarOficinaTerritorioSuprarea").getOutput().getElement());
            
            o = new JSONSerializer().deepSerialize(wsdlResource);
            LOGGER.info("\t" + o);
            
            SOAPClientSAAJ soap = new SOAPClientSAAJ(wsdlResource);
            soap.executeOperation("listarOficinaTerritorioSuprarea");
            
//            LOGGER.info(CadenaUtil.match("$wsdl:portType$wsdl:operation", "(portType|operation)"));
//            LOGGER.info(CadenaUtil.match("$wsdl:types$schema$complexType$sequence$element$", "(types|schema|complexType)"));
//            LOGGER.info(CadenaUtil.match("$wsdl:types$schema$complexType$sequence$element$", "(types|schema|element)"));
//            LOGGER.info(CadenaUtil.match("$wsdl:types$schema$element$", "(types|schema|element)"));
//            
//            String [] o = "$wsdl:types$schema$element$".split("\\$");
//            for(String oq : o) {
//                LOGGER.info("\t" + oq);
//            }
//            LOGGER.info(o.length);

            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SOAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
