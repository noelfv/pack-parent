package com.everis.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.context.junit4.AbstractJUnit4Test;
import org.w3c.dom.Element;

import com.everis.util.xml.DOMReader;
import com.everis.util.xml.DOMReaderException;
import com.everis.util.xml.filter.DOMFilter;

public class WebServiceUtilTest extends AbstractJUnit4Test {
    
    @Test
    public void readWSDL() {
        setUp();
        // WebServiceUtil.readWSDL("http://118.180.36.123/general/services/TablaGeneral/wsdl/TablaGeneral.wsdl");
        
        try {
            /*
            URL url = new URL("http://118.180.36.123/general/services/TablaGeneral/wsdl/TablaGeneral.wsdl");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            */
            
            InputStream in = new FileInputStream("C:/TablaGeneral.wsdl");
            
//            WebServiceUtil x = new WebServiceUtil();
//            Document doc = x.getDocument(in);
//            prettyPrinter(x.getOperations(doc));
            
            List<String> path = new ArrayList<String>();
            path.add("wsdl:portType");
            path.add("wsdl:operation");
            
            // List<Element> e = DOMUtil.obtenerElementos(doc.getFirstChild(), path);
            // prettyPrinter(e.size());
            DOMReader reader = new DOMReader(new DOMFilter() {
                
                private static final String operation = "'wsdl:operation','operation'";
                private Map<String, Element> elements = new HashMap<String, Element>();
                
                @Override
                public void read(Element element) throws DOMReaderException {
                    if(element.getNodeName().indexOf(operation) > -1){
                        LOGGER.error(element.getNodeName());
                    }
                }
            });
            reader.read(in, "/");
            
            // prettyPrinter(doc.getFirstChild().getAttributes().getLength());
            // prettyPrinter(doc.getFirstChild());
            
            // LOGGER.info("\n" + IOUtils.toString(in));
        /*} catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();*/
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DOMReaderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
