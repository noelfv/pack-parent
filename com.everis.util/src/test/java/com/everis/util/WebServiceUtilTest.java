package com.everis.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.springframework.test.context.junit4.AbstractJUnit4Test;

import com.everis.util.xml.DOMReader;
import com.everis.util.xml.DOMReaderException;
import com.everis.webservice.WSDLFilterReader;

public class WebServiceUtilTest extends AbstractJUnit4Test {
    
    @Test
    public void readWSDL() {
        setUp();
        // WebServiceUtil.readWSDL("http://118.180.36.123/general/services/TablaGeneral/wsdl/TablaGeneral.wsdl");
        
        try {
            WSDLFilterReader wsdlFilterReader = new WSDLFilterReader();
            InputStream in = new FileInputStream("C:/TablaGeneral.wsdl");            
            DOMReader reader = new DOMReader(wsdlFilterReader);
            reader.read(in, DOMReader.getPathSeparator());
            
            prettyPrinter(wsdlFilterReader.getOperations());            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DOMReaderException e) {
            e.printStackTrace();
        }
    }
}
