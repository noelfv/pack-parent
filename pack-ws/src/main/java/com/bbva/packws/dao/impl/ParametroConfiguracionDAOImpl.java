package com.bbva.packws.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bbva.packws.dao.ParametroConfiguracionDAO;
import com.bbva.packws.domain.ParametroConfiguracion;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("parametroConfiguracionDAO")
public class ParametroConfiguracionDAOImpl extends HibernateDAO<ParametroConfiguracion> implements ParametroConfiguracionDAO {

    @Override
    public ParametroConfiguracion obtenerParametro(String nombre) {
        Criteria criterioParametro = super.getCriteria(ParametroConfiguracion.class);
        criterioParametro.add(Restrictions.eq("nombre", nombre));

        return (ParametroConfiguracion) criterioParametro.uniqueResult();
    }

}
