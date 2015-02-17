package com.bbva.batch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.batch.dao.ApplicationBatchDAO;
import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.service.ApplicationBatchService;
import com.everis.core.service.impl.DataManipulationService;

@Transactional(propagation = Propagation.REQUIRED)
@Service("applicationBatchService")
public class ApplicationBatchServiceImpl extends DataManipulationService<ApplicationBatch, ApplicationBatchDAO> implements ApplicationBatchService {

    @Autowired
    @Qualifier("applicationBatchDAO")
    public void setHibernateDAO(ApplicationBatchDAO hibernateDAO) {
        super.setHibernateDAO(hibernateDAO);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<ApplicationBatch> listar(String name) {
        return listar(name, false);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<ApplicationBatch> listar(String name, boolean lazy) {
        return getHibernateDAO().listar(name, lazy);
    }
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<ApplicationBatch> listar() {
        return listar(false);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<ApplicationBatch> listar(boolean lazy) {
        return listar("", lazy);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ApplicationBatch obtener(String name) {
        return obtener(name, false);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ApplicationBatch obtener(String name, boolean lazy) {
        return getHibernateDAO().obtener(name, lazy);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ApplicationBatch obtener(Long idApplication) {
        return obtener(idApplication, false);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ApplicationBatch obtener(Long idApplication, boolean lazy) {
        return getHibernateDAO().obtener(idApplication, lazy);
    }
}
