package com.bbva.batch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.batch.dao.StepBatchDAO;
import com.bbva.batch.domain.StepBatch;
import com.bbva.batch.service.StepBatchService;
import com.everis.core.service.impl.DataManipulationService;

@Transactional(propagation = Propagation.REQUIRED)
@Service("stepBatchService")
public class StepBatchServiceImpl extends DataManipulationService<StepBatch, StepBatchDAO> implements StepBatchService {

    @Autowired
    @Qualifier("stepBatchDAO")
    public void setHibernateDAO(StepBatchDAO hibernateDAO) {
        super.setHibernateDAO(hibernateDAO);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<StepBatch> listar(Long idJobBatch) {
        return listar(idJobBatch, false);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<StepBatch> listar(Long idJobBatch, boolean lazy) {
        return getHibernateDAO().listar(idJobBatch, lazy);
    }

}
