package com.everis.web.controller.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.everis.enums.Resultado;
import com.everis.util.TrazaUtil;
import com.everis.web.constant.Produces;
import com.everis.web.controller.AbstractController;
import com.everis.web.model.BaseModel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import flexjson.JSONSerializer;

public abstract class AbstractStrutsControllerImpl extends ActionSupport implements AbstractController {
	
	private static final long serialVersionUID = 1L;
	protected Logger LOG = Logger.getLogger(AbstractStrutsControllerImpl.class);
	protected static Properties prop = null;

	public static void setObjectSession(String name, Object value) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put(name, value);
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	public HttpSession getSession(boolean create) {
		return getRequest().getSession(create);
	}
	
	public static String getMensaje(String key) throws FileNotFoundException, IOException {
		if(prop == null) {
			InputStream is = AbstractStrutsControllerImpl.class.getResourceAsStream("/mensajes.properties");
			prop.load(is);
		}
		return prop.getProperty(key);
	}
	
	public void renderModelJson(Object object) throws IOException{
		String json = "";
		try {
			json =  new JSONSerializer().deepSerialize(object);
		} catch(Exception e) {
			LOG.error("AbstractAction:renderModelJson", e);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"mensaje\": \"");
			sb.append(TrazaUtil.mostrarMensajeHTML(e));
			sb.append("\", \"tipoResultado\": \"ERROR_SISTEMA\"}");
			json = sb.toString();
		}
		this.render(json);
	}
	
	public void renderModelJsonDeepExclude(Object object, String[] exclude) throws IOException {
		String json = "";
		try {
			json =  new JSONSerializer().exclude(exclude).deepSerialize(object);
		} catch(Exception e) {
			LOG.error("AbstractAction:renderModelJsonDeepExclude", e);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"mensaje\": \"");
			sb.append(TrazaUtil.mostrarMensajeHTML(e));
			sb.append("\", \"tipoResultado\": \"ERROR_SISTEMA\"}");
			json = sb.toString();
		}
		this.render(json);
	}
	
	public void renderModelJsonDeepInclude(Object object, String[] include) throws IOException {
		String json = "";
		try {
			json =  new JSONSerializer().include(include).serialize(object);
		} catch(Exception e) {
			LOG.error("AbstractAction:renderModelJsonDeepExclude", e);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"mensaje\": \"");
			sb.append(TrazaUtil.mostrarMensajeHTML(e));
			sb.append("\", \"tipoResultado\": \"ERROR_SISTEMA\"}");
			json = sb.toString();
		}
		this.render(json);
	}
	
	public void renderErrorSistema(Exception ex) throws IOException {
		BaseModel baseModel = new BaseModel();
		baseModel.setMensaje(TrazaUtil.mostrarMensajeHTML(ex));
		baseModel.setTipoResultado(Resultado.ERROR_SISTEMA);
		this.renderModelJson(baseModel);
	}
	
	public void render(String rendered) throws IOException {
		getResponse().setContentType(Produces.JSON);
		PrintWriter out = getResponse().getWriter();		
        LOG.info(rendered);
		out.write(rendered);
		out.flush();
		out.close();
	}
}
