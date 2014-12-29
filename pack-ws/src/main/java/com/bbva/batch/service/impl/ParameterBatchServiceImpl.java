package com.bbva.batch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.batch.dao.ParameterBatchDAO;
import com.bbva.batch.domain.ParameterBatch;
import com.bbva.batch.service.ParameterBatchService;
import com.everis.core.service.impl.DataManipulationService;

@Transactional(propagation = Propagation.REQUIRED)
@Service("parameterBatchService")
public class ParameterBatchServiceImpl extends DataManipulationService<ParameterBatch, ParameterBatchDAO> implements ParameterBatchService {

    @Autowired
    @Qualifier("parameterBatchDAO")
    public void setHibernateDAO(ParameterBatchDAO hibernateDAO) {
        super.setHibernateDAO(hibernateDAO);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<ParameterBatch> listar(Long idStepBatch) {
        return listar(idStepBatch, false);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<ParameterBatch> listar(Long idStepBatch, boolean lazy) {
        return getHibernateDAO().listar(idStepBatch, lazy);
    }

}
