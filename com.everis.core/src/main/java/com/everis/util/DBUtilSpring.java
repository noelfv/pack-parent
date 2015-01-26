package com.everis.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jndi.JndiObjectFactoryBean;

import com.everis.core.exception.BussinesException;

public class DBUtilSpring {

    private static final Logger LOG = Logger.getLogger(DBUtilSpring.class);
    private static boolean envDEV = false;
    private static DBUtilSpring instance = new DBUtilSpring();
    private Map<String, DataSource> dataSources;

    public DBUtilSpring() {
        super();
        dataSources = new HashMap<String, DataSource>();
    }

    public static boolean isEnvDEV() {
        return envDEV;
    }

    public static void setEnvDEV(boolean envDEV) {
        DBUtilSpring.envDEV = envDEV;
    }

    public static DBUtilSpring getInstance() {
        return instance;
    }

    public void setDataSource(String jndiName, DataSource dataSource) {
        dataSources.put(jndiName, dataSource);
    }

    public DataSource getDataSource(String jndiName) {
        DataSource dataSource = null;

        if (!dataSources.containsKey(jndiName)) {
            JndiObjectFactoryBean jndiFactory = new JndiObjectFactoryBean();
            jndiFactory.setJndiName(jndiName);
            jndiFactory.setResourceRef(true);
            jndiFactory.setLookupOnStartup(false);
            jndiFactory.setCache(true);
            jndiFactory.setProxyInterface(DataSource.class);
            try {
                jndiFactory.afterPropertiesSet();
            } catch (IllegalArgumentException e) {
                LOG.error("No se pudo crear el objeto DataSource", e);
            } catch (NamingException e) {
                LOG.error("No se encuentra registrado el nombre del jndi [" + jndiName + "]", e);
            }
            dataSource = (DataSource) jndiFactory.getObject();
            dataSources.put(jndiName, dataSource);
        } else {
            dataSource = dataSources.get(jndiName);
        }

        return dataSource;
    }
    
    public List<Map<String, Object>> executeQuery(String jndi, String query) throws BussinesException {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> row;
        long i1 = System.currentTimeMillis();
        int i = 0;
        int j;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        String[] colNames;
        
        LOG.info("Iniciando el proceso");
        
        try {
            connection = getDataSource(jndi).getConnection();
            statement = connection.createStatement();
            statement.setFetchSize(100);
            resultset = statement.executeQuery(query);
            metadata = resultset.getMetaData();
            
            colNames = new String[metadata.getColumnCount()];
            for(j = 1; j <= colNames.length; j++) {
                colNames[j - 1] = metadata.getColumnName(j);
            }
            
            while(resultset.next()) {
                row = new HashMap<String, Object>();
                for(j = 0; j < colNames.length; j++) {
                    row.put(colNames[j], resultset.getObject(colNames[j]));
                }
                result.add(row);
                i++;
            }
        } catch(SQLException e) {
            throw new BussinesException("No se pudo ejecutar la consulta: [" + query + "]", e);
        } finally {
            if(resultset != null) {
                try {
                    resultset.close();
                } catch (SQLException e) {
                    LOG.error("Lectura de base de datos:CLOSE RESULTSET", e);
                }
            }

            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOG.error("Lectura de base de datos:CLOSE STATEMENT", e);
                }
            }

            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.error("Lectura de base de datos:CLOSE CONNECTION", e);
                }
            }
        }

        long i2 = System.currentTimeMillis();
        LOG.info("Proceso Terminado");
        LOG.info("Lineas leidas:" + i);
        LOG.info("Demoro:" + ((i2 - i1) / 1000) + " segundos");
        
        return result;
    }
    
    public String executeQueryUniqueResult(String jndi, String query) throws BussinesException {
        String result = null;
        long i1 = System.currentTimeMillis();
        int i = 0;
        int j = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        String[] colNames;
        
        LOG.info("Iniciando el proceso");
        
        try {
            connection = getDataSource(jndi).getConnection();
            statement = connection.createStatement();
            statement.setFetchSize(100);
            resultset = statement.executeQuery(query);
            metadata = resultset.getMetaData();
            
            colNames = new String[metadata.getColumnCount()];
            if(colNames.length > 1) {
                throw new ArrayIndexOutOfBoundsException("El numero de campos de resultados supera lo esperado");
            }
            for(j = 1; j <= colNames.length; j++) {
                colNames[j - 1] = metadata.getColumnName(j);
            }
            
            while(resultset.next()) {
                result = resultset.getString(colNames[0]);
                i++;
            }
        } catch(SQLException e) {
            throw new BussinesException("No se pudo ejecutar la consulta: [" + query + "]", e);
        } catch(Exception e) {
            throw new BussinesException("Error al procesar la consulta: [" + query + "]", e);
        } finally {
            if(resultset != null) {
                try {
                    resultset.close();
                } catch (SQLException e) {
                    LOG.error("Lectura de base de datos:CLOSE RESULTSET", e);
                }
            }

            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOG.error("Lectura de base de datos:CLOSE STATEMENT", e);
                }
            }

            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.error("Lectura de base de datos:CLOSE CONNECTION", e);
                }
            }
        }

        long i2 = System.currentTimeMillis();
        LOG.info("Proceso Terminado");
        LOG.info("Lineas leidas:" + i);
        LOG.info("Demoro:" + ((i2 - i1) / 1000) + " segundos");
        
        return result;
    }
    
    public boolean execute(String jndi, String query) throws BussinesException {
        boolean result = true;
        Connection connection = null;
        Statement statement = null;
        
        try {
            connection = getDataSource(jndi).getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            result = statement.execute(query);
            
            try {
                connection.commit();
            } catch (SQLException e) {
                LOG.error("NOT Commit : [" + query + "]");
                connection.rollback();
                result = false;
            }

        } catch (SQLException e) {
            throw new BussinesException("No se pudo ejecutar la consulta: [" + query + "]", e);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOG.error("Lectura de base de datos:CLOSE STATEMENT", e);
                }
            }

            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.error("Lectura de base de datos:CLOSE CONNECTION", e);
                }
            }
        }
        
        return result;
    }
}
