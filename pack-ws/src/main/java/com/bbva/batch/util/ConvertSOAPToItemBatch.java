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
    private Map<String, String> keys;
    private Map<String, Integer> root;
    private String previousKey;
    private ItemBatch previous;
    
    private void setKeyElement(String prefix, String prefixContent, WSDLElement wsdlElement, Map<String, WSDLElement> elements) {
        int typeObject; 
        if(wsdlElement != null) {
            WSDLElement attributeRef;
            keys.put(prefix, prefixContent + "root");
            for(WSDLElement attribute : wsdlElement.getAttributes()) {
                typeObject = CadenaUtil.match(attribute.getType(), SOAPClientSAAJ.wsdlDataTypes);
                if(prefixContent.isEmpty()) {
                    root.put(prefix + attribute.getName(), typeObject);
                }
                if(typeObject == 1) {
                    keys.put(prefix + attribute.getName(), prefixContent + attribute.getName());
                } else {
                    attributeRef = elements.get(attribute.getType());
                    setKeyElement(prefix + attribute.getName() + "$", prefixContent + attribute.getName() + ".", attributeRef, elements);
                }
            }
        }
    }
    
    public ConvertSOAPToItemBatch(WSDLMessage output, Map<String, WSDLElement> elements) {
        super();
        this.items = new ArrayList<ItemBatch>();
        this.keys = new HashMap<String, String>();
        this.root = new HashMap<String, Integer>();
        setKeyElement("$S:Body$ns2:" + output.getName() + "$", "", output.getElement(), elements);
    }

    private ItemBatch createEntity(Element element, String path) {
        ItemBatch o = new ItemBatch();
        
        if(root.get(path) == 1) {
            o.setObject(keys.get(path), element.getTextContent());
        }
        
        return o;
    }
    
    private void createAttribute(ItemBatch item, Element element, String path) {
        item.setObject(keys.get(path), element.getTextContent());
    }
    
    @Override
    public void read(Element element, String path, short nivel) throws DOMReaderException {
        if(root.containsKey(path)) {
            previous = createEntity(element, path);
            items.add(previous);
            LOGGER.debug("ROOT:" + path);
        } else if(keys.containsKey(path)){
            createAttribute(previous, element, path); 
            LOGGER.debug("CreateObject:" + path);
        } else {
            LOGGER.debug("NOT CreateObject:" + path);
        }
    }

    public List<ItemBatch> getItems() {
        return items;
    }

    public Map<String, String> getKeys() {
        return keys;
    }
}
