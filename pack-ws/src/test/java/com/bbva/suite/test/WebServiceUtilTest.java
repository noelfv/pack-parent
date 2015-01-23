package com.bbva.suite.test;

import org.junit.Test;
import org.springframework.test.context.junit4.AbstractJUnit4Test;

public class WebServiceUtilTest extends AbstractJUnit4Test {
    
    @Test
    public void readWSDL() {
        setUp();
//        
//        try {
//                
//            WSDLResource wsdlResource = new WSDLResource("http://192.168.247.1:8081/CentrosBBVAWS/CentrosBBVAWebService?wsdl");
//            wsdlResource.getOperation("listarOficinaTerritorioSuprarea").getInput().getElement().setAttribute("term", "%");
//            
//            String o = new JSONSerializer().deepSerialize(wsdlResource);
//            JSONDeserializer<WSDLResource> jsonDeserializer = new JSONDeserializer<WSDLResource>();
//            wsdlResource = jsonDeserializer.deserialize(o);
//
//            LOGGER.info("\t" + o);            
////          .use("elements", WSDLElement.class)
////          .use("messages", WSDLMessage.class);
//            
//            
//            LOGGER.info("targeNamespace: " + wsdlResource.getTargetNamespace());
//            LOGGER.info("Elements: " + wsdlResource.getElements().size());
//            
//            ConvertSOAPToItemBatch c = new ConvertSOAPToItemBatch(wsdlResource.getOperation("listarOficinaTerritorioSuprarea").getOutput(), wsdlResource.getElements());
//            
//            prettyPrinter(wsdlResource.getOperation("listarOficinaTerritorioSuprarea").getOutput().getElement());
//            prettyPrinter(c.getKeys());
//            
////            SOAPClientSAAJ soap = new SOAPClientSAAJ(wsdlResource);
////            soap.executeOperation("listarOficinaTerritorioSuprarea");
//
//            WSDLItemReader wsdlItemReader = new WSDLItemReader();
//            wsdlItemReader.setOperation("listarOficinaTerritorioSuprarea");
//            wsdlItemReader.setWsdlResource(wsdlResource);
//            // wsdlItemReader.open(new ExecutionContext());
//            
//            SOAPClientSAAJ soap = new SOAPClientSAAJ(wsdlResource);
//            ByteArrayOutputStream bos = soap.executeOperation("listarOficinaTerritorioSuprarea");
//            ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
//            ConvertSOAPToItemBatch convertFilter = new ConvertSOAPToItemBatch(wsdlResource.getOperation("listarOficinaTerritorioSuprarea").getOutput(), wsdlResource.getElements());
//            LOGGER.info(convertFilter.getKeys());
//            
//            DOMReader reader = new DOMReader(bais, convertFilter);
//            reader.read();
//            List<ItemBatch> items = new ArrayList<ItemBatch>();
//            items.addAll(convertFilter.getItems());
//            
//            byte[] rule = null;
//            
//            File ruleFile = new File("C:\\jquedena\\Proyectos\\workspace-pack-conele\\pack-parent\\pack-ws\\src\\main\\resources\\EvalCargaTerritorio.drl");
//            InputStream input = new FileInputStream(ruleFile);
//            ByteArrayOutputStream output = new ByteArrayOutputStream(); 
//            
//            IOUtils.copy(input, output);
//            
//            rule = output.toByteArray();
//            LOGGER.info(new String(rule));
//            DroolsRuleRunner evaluator = new DroolsRuleRunner();
//            evaluator.runRules(new byte[][]{rule}, items.toArray());
//            
//            LOGGER.info("Elements: " + items.size());
//            prettyPrinter(items);
//        } catch (UnsupportedOperationException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
