package com.bbva.drools;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.builder.conf.PropertySpecificOption;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class DroolsRuleRunner {
    
    private static final Logger LOGGER = Logger.getLogger(DroolsRuleRunner.class);
    
    public DroolsRuleRunner() {
    }

    public void runRules(byte[][] rules, Object[] facts) {
        KnowledgeBuilderConfiguration kbuilderConf;
        KnowledgeBuilder kbuilder;
        
        kbuilderConf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
        kbuilderConf.setOption(PropertySpecificOption.ALWAYS);
        kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(kbuilderConf);   
        
        for(byte[] rule : rules) {
            kbuilder.add(ResourceFactory.newByteArrayResource(rule), ResourceType.DRL);
        }
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error : errors) {
                LOGGER.info(error);
                
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());                       
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        
        for (Object data : facts) {  
            ksession.insert(data);
        }
        
        ksession.fireAllRules();
    }
}
