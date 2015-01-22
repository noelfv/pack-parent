package com.bbva.batch.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import com.bbva.batch.domain.ItemBatch;
import com.everis.util.CadenaUtil;
import com.everis.util.xml.DOMReaderException;
import com.everis.util.xml.filter.DOMFilter;
import com.everis.webservice.SOAPClientSAAJ;
import com.everis.webservice.WSDLElement;
import com.everis.webservice.WSDLMessage;

public class ConvertSOAPToItemBatch implements DOMFilter {

    private static final Logger LOGGER = Logger.getLogger(ConvertSOAPToItemBatch.class);
    private List<ItemBatch> items;
    private Map<String, Map<String, String>> keys; 
    
    private void setKeyElement(String prefix, WSDLElement wsdlElement, Map<String, WSDLElement> elements) {
        if(wsdlElement != null) {
            WSDLElement attributeRef;
            keys.put(prefix, new HashMap<String, String>());
            for(WSDLElement attribute : wsdlElement.getAttributes()) {
                if(CadenaUtil.match(attribute.getType(), SOAPClientSAAJ.wsdlDataTypes) == 1) {
                    keys.get(prefix).put(prefix + attribute.getName() + "$", attribute.getName());
                } else {
                    attributeRef = elements.get(attribute.getType());
                    setKeyElement(prefix + attribute.getName() + "$", attributeRef, elements);
                }
            }
        }
    }
    
    public ConvertSOAPToItemBatch(WSDLMessage output, Map<String, WSDLElement> elements) {
        super();
        this.items = new ArrayList<ItemBatch>();
        this.keys = new HashMap<String, Map<String, String>>();
        setKeyElement("$S:Body$ns2:" + output.getName(), output.getElement(), elements);
    }

    @Override
    public void read(Element element, String path, short nivel) throws DOMReaderException {

    }

    public List<ItemBatch> getItems() {
        return items;
    }

    public Map<String, Map<String, String>> getKeys() {
        return keys;
    }
}
