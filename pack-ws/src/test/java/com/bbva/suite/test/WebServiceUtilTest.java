package com.bbva.suite.test;

import org.junit.Test;
import org.springframework.test.context.junit4.AbstractJUnit4Test;

import com.bbva.batch.file.WSDLItemReader;
import com.bbva.batch.util.ConvertSOAPToItemBatch;
import com.everis.webservice.WSDLResource;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class WebServiceUtilTest extends AbstractJUnit4Test {
    
    @Test
    public void readWSDL() {
        setUp();
        
        try {
                
            WSDLResource wsdlResource = new WSDLResource("http://192.168.247.1:8081/CentrosBBVAWS/CentrosBBVAWebService?wsdl");
            wsdlResource.getOperation("listarOficinaTerritorioSuprarea").getInput().getElement().setAttribute("term", "%");
            
            String o = new JSONSerializer().deepSerialize(wsdlResource);
            JSONDeserializer<WSDLResource> jsonDeserializer = new JSONDeserializer<WSDLResource>();
            wsdlResource = jsonDeserializer.deserialize(o);

            LOGGER.info("\t" + o);            
//          .use("elements", WSDLElement.class)
//          .use("messages", WSDLMessage.class);
            
            
            LOGGER.info("targeNamespace: " + wsdlResource.getTargetNamespace());
            LOGGER.info("Elements: " + wsdlResource.getElements().size());
            
            ConvertSOAPToItemBatch c = new ConvertSOAPToItemBatch(wsdlResource.getOperation("listarOficinaTerritorioSuprarea").getOutput(), wsdlResource.getElements());
            
            prettyPrinter(wsdlResource.getOperation("listarOficinaTerritorioSuprarea").getOutput().getElement());
            prettyPrinter(c.getKeys());
            
//            SOAPClientSAAJ soap = new SOAPClientSAAJ(wsdlResource);
//            soap.executeOperation("listarOficinaTerritorioSuprarea");

            WSDLItemReader wsdlItemReader = new WSDLItemReader();
            wsdlItemReader.setOperation("listarOficinaTerritorioSuprarea");
            wsdlItemReader.setWsdlResource(wsdlResource);
            
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }
}
