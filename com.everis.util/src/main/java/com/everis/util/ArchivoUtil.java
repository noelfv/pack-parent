package com.everis.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * {@code ArchivoUtil}, contiene metodos genericos para manejo de archivos
 * @version 1.0
 * @author jquedena
 */
public class ArchivoUtil {

	private static final Logger logger = Logger.getLogger(ArchivoUtil.class);
	private static final int BUFFER_SIZE = 1024;
	
	private static void cerrar(Closeable recurso, File archivo) {
		if (recurso != null) {
			try {
				recurso.close();
			} catch (IOException e) {
				logger.error("Archivo:cerrar:" + archivo.getPath());
				logger.error("Archivo:cerrar", e);
			}
		}
	}
	
	/**
	 * Crea un directorio
	 * @param archivo {@link File}
	 * @throws IOException
	 */
	public static void crearDirectorio(File archivo) throws IOException {
		if (archivo.exists() && !archivo.isFile()) {
			throw new IOException(archivo.getPath() + " es un directorio");
		}
		File parentFile = archivo.getParentFile();
		if (!parentFile.exists() && !parentFile.mkdirs()) {
			throw new IOException("Creacion de directorios " + parentFile.getPath() + " fallida");
		}
	}

	/**
	 * Copia un archivo
	 * @param origen {@link File}
	 * @param destino {@link File}
	 * @param reescribir {@link Boolean}, {@code true} indica que se sobreescribira el archivo
	 * @throws IOException
	 */
	public static void copiar(File origen, File destino, boolean reescribir)
			throws IOException {
		if (destino.exists() && !reescribir) {
			throw new IOException("La copia del archivo " + origen.getPath()
					+ " a " + destino.getPath()
					+ " no se puede realizar, el archivo de destino existe.");
		}

		crearDirectorio(destino);
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			input = new BufferedInputStream(new FileInputStream(origen));
			output = new BufferedOutputStream(new FileOutputStream(destino));
			int data = -1;
			while ((data = input.read()) != -1) {
				output.write(data);
			}
		} catch (Exception e) {
			logger.error("Archivo:copiar", e);
		} finally {
			cerrar(input, origen);
			cerrar(output, destino);
		}
	}

	/**
	 * Mueve un archivo
	 * @param origen {@link File}
	 * @param destino {@link File}
	 * @param sobreescribir {@link Boolean}, {@code true} indica que se sobreescribira el archivo
	 * @throws IOException
	 */
	public static void mover(File origen, File destino, boolean sobreescribir)
			throws IOException {
		if (destino.exists()) {
			if (sobreescribir) {
				destino.delete();
			} else {
				throw new IOException("El archivo " + origen.getPath()
						+ " no se puede mover a " + destino.getPath()
						+ ", el archivo de destino existe.");
			}
		}

		if (origen.exists()) {
			crearDirectorio(destino);
			if (!origen.renameTo(destino)) {
				throw new IOException("Error al mover el archivo de "
						+ origen.getPath() + " a " + destino.getPath() + ".");
			}
		} else {
			throw new FileNotFoundException("El archivo " + origen.getName()
					+ " no existe.");
		}
	}

	/**
	 * Renombra un archivo
	 * @param origen {@link File}
	 * @param destino {@link File}
	 * @throws IOException
	 */
	public static void renombrar(File origen, File destino) throws IOException {
		if (origen.exists()) {
			if (origen.renameTo(destino)) {
				logger.error("Se renombro con exito el archivo.");
			} else {
				throw new IOException("No se pudo renombrar el archivo.");
			}
		} else {
			throw new FileNotFoundException("El archivo " + origen.getName()
					+ " no existe.");
		}
	}

	/**
	 * Devuelve un arrego con los archivos de un directorio dado
	 * @param archivo {@link File}
	 * @return {@link File}[], Si archivo no es un directorio retorna {@code null}
	 * @throws IOException
	 */
	public static File[] listarArchivos(File archivo) throws IOException {
		if (archivo != null && archivo.isDirectory()) {
			return archivo.listFiles();
		} else {
			return null;
		}
	}
	
	/**
	 * Comprime un archivo
	 * @param pFile {@link String}
	 * @param pZipFile {@link String}
	 * @throws Exception
	 */
    public void zip(String pFile, String pZipFile) throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		ZipOutputStream zipos = null;

		byte[] buffer = new byte[BUFFER_SIZE];
		try {
			fis = new FileInputStream(pFile);
			fos = new FileOutputStream(pZipFile);
			zipos = new ZipOutputStream(fos);
			ZipEntry zipEntry = new ZipEntry(pFile);
			zipos.putNextEntry(zipEntry);
			int len = 0;
			while ((len = fis.read(buffer, 0, BUFFER_SIZE)) != -1) {
				zipos.write(buffer, 0, len);
			}
			zipos.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			zipos.close();
			fis.close();
			fos.close();
		}
	}
    
    /**
     * Escribe un archivo a partir de un arreglo de bytes
     * @param archivo {@link String}
     * @param valor {@code byte[]}
     * @return {@link File}, retorna el archivo creado
     * @throws IOException
     */
    public static File escribirArchivo(String archivo, byte[] valor) throws IOException {
		File file = new File(archivo);
		BufferedOutputStream output = null;

		if(file.exists()) {
			if(file.isDirectory()) {
				throw new IOException(file.getAbsolutePath() + " es un directorio.");
			}
		} else {
			crearDirectorio(file);
		}
		
		try {
			output = new BufferedOutputStream(new FileOutputStream(file));
			output.write(valor);
		} catch (Exception e) {
			logger.error("ArchivoUtil:escribirArchivo", e);
		} finally {
			cerrar(output, file);
		}
		
		return file;
	}
	
    /**
     * Escribe un archivo a partir de una cadena
     * @param archivo {@link String}
     * @param valor {@link String}
     * @return {@link File}, retorna el archivo creado
     * @throws IOException
     */
	public static File escribirArchivo(String archivo, String valor) throws IOException {
		return escribirArchivo(archivo, valor.getBytes());
	}
	
	/**
	 * Obtiene el {@link InputStream} de un archivo
	 * @param archivo {@link File}
	 * @return {@link InputStream}
	 * @throws FileNotFoundException
	 */
	public static InputStream obtenerArchivo(File archivo) throws FileNotFoundException {
		InputStream input = null;

		if(archivo.exists()) {
			input = new FileInputStream(archivo);
		} else {
			throw new FileNotFoundException();
		}
		
		return input;
	}
	
	/**
	 * Obtiene el {@link InputStream} de un archivo
	 * @param archivo {@link String}
	 * @return {@link InputStream}
	 * @throws FileNotFoundException
	 */
	public static InputStream obtenerArchivo(String archivo) throws FileNotFoundException {
		return obtenerArchivo(new File(archivo));
	}
}
