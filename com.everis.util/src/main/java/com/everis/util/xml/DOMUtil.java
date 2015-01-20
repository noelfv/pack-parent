package com.everis.util.xml;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

public class DOMUtil {

    private static final Logger LOGGER = Logger.getLogger(DOMUtil.class);
    
    @SuppressWarnings("unchecked")
    public static <T> T createObject(Class<?> clazz, Element element, String[][] values) {
        Object o = null;
        int i;
        
        try {
            o = clazz.newInstance();
            
            for(i = 0; i < values.length; i++) {
                BeanUtilsBean.getInstance().setProperty(o, values[i][0], element.getAttribute(values[i][1]));
            }
        } catch (InstantiationException e) {
            LOGGER.error("", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("", e);
        } catch (InvocationTargetException e) {
            LOGGER.error("", e);
        }

        return (o == null ? null : (T) o);
    }
}
