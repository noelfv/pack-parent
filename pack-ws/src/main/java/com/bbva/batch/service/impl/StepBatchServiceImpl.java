package com.bbva.batch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.batch.dao.StepBatchDAO;
import com.bbva.batch.domain.StepBatch;
import com.bbva.batch.service.StepBatchService;

@Service("stepBatchService")
public class StepBatchServiceImpl implements StepBatchService {

    @Resource(name = "stepBatchDAO")
    private StepBatchDAO stepBatchDAO;

    @Transactional(readOnly = true)
    public List<StepBatch> listar(Long idJobBatch) {
        return stepBatchDAO.listar(idJobBatch);
    }

    @Transactional
    public void insertar(StepBatch o) {
        stepBatchDAO.save(o);
    }

    @Transactional
    public void actualizar(StepBatch o) {
        stepBatchDAO.saveOrUpdate(o);
    }

    @Transactional
    public void eliminar(StepBatch o) {
        stepBatchDAO.delete(o);
    }
}
