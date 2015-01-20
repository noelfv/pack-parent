package com.everis.webservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import com.everis.util.CadenaUtil;
import com.everis.util.xml.DOMReaderException;
import com.everis.util.xml.DOMUtil;
import com.everis.util.xml.filter.DOMFilter;

public class WSDLFilterReader implements DOMFilter {

    private static final Logger LOGGER = Logger.getLogger(WSDLFilterReader.class);
    private String wsdlOperation = "(portType|operation)";
    private String[] wsdlInputOutput = new String[]{"(portType|operation|input)", "(portType|operation|output)"};
    private Map<String, Operation> operations = new HashMap<String, Operation>();
    private Operation previous;
    
    private Operation createOperation(Element element, String path) {
        Operation o = null;
        if(CadenaUtil.match(path, wsdlOperation) == 2) {
            o = DOMUtil.createObject(Operation.class, element, new String[][]{new String[]{"name", "name"}});
        }
        
        return o;
    }
    
    private void createMessage(Element element, String path, Operation operation) {
        Message o = null;
        if(CadenaUtil.match(path, wsdlInputOutput[0]) == 3) {
            o = DOMUtil.createObject(Message.class, element, new String[][]{new String[]{"name", "name"}});
            operation.setInput(o);
        } else if(CadenaUtil.match(path, wsdlInputOutput[1]) == 3) {
            o = DOMUtil.createObject(Message.class, element, new String[][]{new String[]{"name", "name"}});
            operation.setOutput(o);
        }
    }
    
    public void proccessRead(Element element, String path, short nivel) {
        if(nivel == DOMFilter.NODE) {
            previous = createOperation(element, path);
            if(previous != null) {
                operations.put(previous.getName(), previous);
                LOGGER.info(previous.getName() + "####" + System.currentTimeMillis());
            }
        } else if(nivel == DOMFilter.ELEMENT) {
            if(previous != null) {
                createMessage(element, path, previous);
            }
        }
        
        LOGGER.error(element.getNodeName() + "%" + nivel);
    }
    
    @Override
    public void read(Element element, String path, short nivel) throws DOMReaderException {
        if(CadenaUtil.match(path, wsdlOperation) == 2){
            proccessRead(element, path, nivel);
        }
    }

    public Map<String, Operation> getOperations() {
        return operations;
    }

    public void setOperations(Map<String, Operation> operations) {
        this.operations = operations;
    }
}
