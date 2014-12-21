package com.bbva.packws.service.impl;

import javax.annotation.Resource;
import java.util.List;

import com.bbva.packws.GenericTest;
import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.service.SolicitudService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
public class SolicitudServiceImplTest extends GenericTest {
	
	@Resource(name = "solicitudService")
	private SolicitudService solicitudService;

	@Test
	public void consultarSolicitudesUnificadoSinProductosinEstado1() {
		Solicitud ultimoRegistro = null;
        Integer totalRegistros = 0;
        // String tipoDOI, String numDOI,String[] codigoProducto,String[] estado, Solicitud ultimoRegistro, int nroRegistro, boolean iiceActivo
		List<Solicitud> list = solicitudService.consultarSolicitudes("L","41841858", new String[]{}, new String[]{}, ultimoRegistro, 2, true);
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
        // String tipoDOI, String numDOI,String[] codigoProducto,String[] estado, Solicitud ultimoRegistro, int nroRegistro, boolean iiceActivo
        List<Solicitud> list = solicitudService.consultarSolicitudes("L","41841858", new String[]{""}, new String[]{""}, ultimoRegistro, 2, true);
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
        // String tipoDOI, String numDOI,String[] codigoProducto,String[] estado, Solicitud ultimoRegistro, int nroRegistro, boolean iiceActivo
        List<Solicitud> list = solicitudService.consultarSolicitudes("L","41841858", new String[]{"96"}, new String[]{""}, ultimoRegistro, 2, true);
        Assert.assertNotNull("Lista invalida", list);
        Assert.assertTrue("Lista con valores", !list.isEmpty());
        Assert.assertTrue("No cumple con el resultado esperado", list.size() == 2);
        prettyPrinter(list);
        LOGGER.info("Total de registros: " + totalRegistros);
    }

    @Test
    public void consultarSolicitudesUnificadoPLDSinProductoSinEstado1() {
        Solicitud ultimoRegistro = null;
        // String tipoDOI, String numDOI,String[] codigoProducto,String[] estado, Solicitud ultimoRegistro, int nroRegistro, boolean iiceActivo
        List<Solicitud> list = solicitudService.consultarSolicitudes("L","41841858", new String[]{"   "}, new String[]{"    "}, ultimoRegistro, 33, false);
        Integer totalRegistros = solicitudService.contarSolicitudes("L","41841858", new String[]{"   "}, new String[]{"    "}, false);
        Assert.assertNotNull("Lista invalida", list);
        Assert.assertTrue("Lista con valores", !list.isEmpty());
        Assert.assertTrue("No cumple con el resultado esperado", list.size() == 33);
        // prettyPrinter(list);

        printer(list);

        LOGGER.info("Total de registros: " + totalRegistros);
    }
}
