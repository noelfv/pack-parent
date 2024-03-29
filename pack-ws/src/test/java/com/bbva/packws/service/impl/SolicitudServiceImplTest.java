package com.bbva.packws.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.service.SolicitudService;
import com.bbva.quartz.service.TriggerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
public class SolicitudServiceImplTest extends AbstractJUnit4Test {

    @Resource(name = "solicitudService")
    private SolicitudService solicitudService;

    @Resource(name = "triggerService")
    private TriggerService triggerService;
    
    @Test
    public void listarTrigger() {
        prettyPrinter(triggerService.listar());
    }
    
    @Test
    public void consultarSolicitudesUnificadoSinProductosinEstado1() {
        Solicitud ultimoRegistro = null;
        Integer totalRegistros = 0;
        /***
         * String tipoDOI, String numDOI,String[] codigoProducto,String[]
         * estado, Solicitud ultimoRegistro, int nroRegistro, boolean iiceActivo
         **/
        List<Solicitud> list = solicitudService.consultarSolicitudes("L", "41841858", new String[]{}, new String[]{}, ultimoRegistro, 2, true);
        Assert.assertNotNull("Lista invalida", list);
        Assert.assertTrue("Lista con valores", !list.isEmpty());
        Assert.assertTrue("No cumple con el resultado esperado", list.size() == 2);
        prettyPrinter(list);
        LOGGER.info("Total de registros: " + totalRegistros);
    }

    @Test
    public void consultarSolicitudesUnificadoSinProductoSinEstado2() {
        Solicitud ultimoRegistro = null;
        Integer totalRegistros = 0;
        List<Solicitud> list = solicitudService.consultarSolicitudes("L", "41841858", new String[]{""}, new String[]{""}, ultimoRegistro, 2, true);
        Assert.assertNotNull("Lista invalida", list);
        Assert.assertTrue("Lista con valores", !list.isEmpty());
        Assert.assertTrue("No cumple con el resultado esperado", list.size() == 2);
        prettyPrinter(list);
        LOGGER.info("Total de registros: " + totalRegistros);
    }

    @Test
    public void consultarSolicitudesUnificadoConProductoSinEstado1() {
        Solicitud ultimoRegistro = null;
        Integer totalRegistros = 0;
        List<Solicitud> list = solicitudService.consultarSolicitudes("L", "41841858", new String[]{"96"}, new String[]{""}, ultimoRegistro, 2, true);
        Assert.assertNotNull("Lista invalida", list);
        Assert.assertTrue("Lista con valores", !list.isEmpty());
        Assert.assertTrue("No cumple con el resultado esperado", list.size() == 2);
        prettyPrinter(list);
        LOGGER.info("Total de registros: " + totalRegistros);
    }

    @Test
    public void consultarSolicitudesUnificadoPLDSinProductoSinEstado1() {
        Solicitud ultimoRegistro = null;
        List<Solicitud> list = solicitudService.consultarSolicitudes("L", "41841858", new String[]{"   "}, new String[]{"    "}, ultimoRegistro, 33, false);
        Integer totalRegistros = solicitudService.contarSolicitudes("L", "41841858", new String[]{"   "}, new String[]{"    "}, false);
        Assert.assertNotNull("Lista invalida", list);
        Assert.assertTrue("Lista con valores", !list.isEmpty());
        Assert.assertTrue("No cumple con el resultado esperado", list.size() == 33);
        printer(list);
        LOGGER.info("Total de registros: " + totalRegistros);
    }
}
