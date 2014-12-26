package com.bbva.batch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.batch.dao.ParameterBatchDAO;
import com.bbva.batch.domain.ParameterBatch;
import com.bbva.batch.service.ParameterBatchService;

@Service("parameterBatchService")
public class ParameterBatchServiceImpl implements ParameterBatchService {

    @Resource(name = "parameterBatchDAO")
    private ParameterBatchDAO parameterBatchDAO;

    public List<ParameterBatch> listar(Long idStepBatch) {
        return listar(idStepBatch, false);
    }
    
    @Transactional(readOnly = true)
    public List<ParameterBatch> listar(Long idStepBatch, boolean lazy) {
        return parameterBatchDAO.listar(idStepBatch, lazy);
    }

    @Transactional
    public void insertar(ParameterBatch o) {
        parameterBatchDAO.save(o);
    }

    @Transactional
    public void actualizar(ParameterBatch o) {
        parameterBatchDAO.saveOrUpdate(o);
    }

    @Override
    public void actualizar(List<ParameterBatch> o) {
        parameterBatchDAO.saveOrUpdateAll(o);
    }

    @Transactional
    public void eliminar(ParameterBatch o) {
        parameterBatchDAO.delete(o);
    }
}
