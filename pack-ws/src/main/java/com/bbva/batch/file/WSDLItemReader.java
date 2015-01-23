package com.bbva.batch.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.util.ConvertSOAPToItemBatch;
import com.bbva.drools.DroolsRuleRunner;
import com.everis.util.xml.DOMReader;
import com.everis.webservice.SOAPClientSAAJ;
import com.everis.webservice.WSDLResource;

public class WSDLItemReader extends AbstractItemCountingItemStreamItemReader<ItemBatch> implements InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(WSDLItemReader.class);
    private WSDLResource wsdlResource;
    private String operation;
    private List<ItemBatch> items;
    private boolean isOpen = false;
    private byte[] rule;

    /***
    public static void main(String args[]) {
        WSDLItemReader item = new WSDLItemReader();
        try {
            WSDLResource wsdlResource = new WSDLResource("http://192.168.247.1:8081/CentrosBBVAWS/CentrosBBVAWebService?wsdl");
            wsdlResource.getOperation("listarOficinaTerritorioSuprarea").getInput().getElement().setAttribute("term", "%");
            item.setWsdlResource(wsdlResource);
            item.setOperation("listarOficinaTerritorioSuprarea");
            item.doOpen();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    @Override
    protected ItemBatch doRead() throws Exception {
        return this.getCurrentItemCount() > items.size() ? null : items.get(this.getCurrentItemCount() - 1);
    }

    @Override
    protected void doOpen() throws Exception {
        if(!isOpen) {
            SOAPClientSAAJ soap = new SOAPClientSAAJ(wsdlResource);
            ByteArrayOutputStream bos = soap.executeOperation(operation);
            ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
            ConvertSOAPToItemBatch convertFilter = new ConvertSOAPToItemBatch(wsdlResource.getOperation(operation).getOutput(), wsdlResource.getElements());
            LOGGER.info(convertFilter.getKeys());
            
            DOMReader reader = new DOMReader(bais, convertFilter);
            reader.read();
            items = new ArrayList<ItemBatch>();
            items.addAll(convertFilter.getItems());
            LOGGER.error("Elements: " + items.size());
            
            if(rule != null && rule.length > 0) {
                DroolsRuleRunner evaluator = new DroolsRuleRunner();
                evaluator.runRules(new byte[][]{rule}, items.toArray());
            }
            
            isOpen = true;
        }
    }

    @Override
    protected void doClose() throws Exception {
    }

    public WSDLResource getWsdlResource() {
        return wsdlResource;
    }

    public void setWsdlResource(WSDLResource wsdlResource) {
        this.wsdlResource = wsdlResource;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public byte[] getRule() {
        return rule;
    }

    public void setRule(byte[] rule) {
        this.rule = rule;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        doOpen();
    }
}
