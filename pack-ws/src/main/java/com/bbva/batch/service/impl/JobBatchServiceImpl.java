package com.bbva.batch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.batch.dao.JobBatchDAO;
import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.service.JobBatchService;
import com.everis.core.service.impl.DataManipulationService;

@Transactional(propagation = Propagation.REQUIRED)
@Service("jobBatchService")
public class JobBatchServiceImpl extends DataManipulationService<JobBatch, JobBatchDAO> implements JobBatchService {

    @Autowired
    @Qualifier("jobBatchDAO")
    public void setHibernateDAO(JobBatchDAO hibernateDAO) {
        super.setHibernateDAO(hibernateDAO);
    }
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<JobBatch> listar(Long idApplicationBatch) {
        return listar(idApplicationBatch, false);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<JobBatch> listar(Long idApplicationBatch, boolean lazy) {
        return getHibernateDAO().listar(idApplicationBatch, lazy);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public JobBatch obtener(Long idJobBatch) {
        return obtener(idJobBatch, false);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public JobBatch obtener(Long idJobBatch, boolean lazy) {
        return getHibernateDAO().obtener(idJobBatch, lazy);
    }
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public JobBatch obtener(Long idApplicationBatch, String name) {
        return obtener(idApplicationBatch, name, false);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public JobBatch obtener(Long idApplicationBatch, String name, boolean lazy) {
        return getHibernateDAO().obtener(idApplicationBatch, name, lazy);
    }
}
