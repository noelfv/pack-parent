package com.everis.mail;

import java.io.File;

import com.everis.enums.Contenido;

public class Attachment {

	private Contenido contentType;
	private File file;

	public Contenido getContentType() {
		return contentType;
	}

	public void setContentType(Contenido contentType) {
		this.contentType = contentType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void setFile(String file) {
		this.file = new File(file);
	}
}
