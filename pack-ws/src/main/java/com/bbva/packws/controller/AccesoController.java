package com.bbva.packws.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("accesoController")
@Scope("prototype")
@RequestMapping(value = {"", "scheduler"})
public class AccesoController {

	@RequestMapping(value="signIn")
	public String signIn() {

        urn "common/sign-in";
	}
	
	@RequestMapping(value="signOut")
	public String signOut() {
		return "common/sign-out";
	}
	
	@RequestMapping(value="notAuthorized")
	public String notAuthorized() {
		return "common/not-authorized";
	}
}
