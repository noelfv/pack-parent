package com.bbva.batch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.batch.dao.JobBatchDAO;
import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.service.JobBatchService;

@Service("jobBatchService")
public class JobBatchServiceImpl implements JobBatchService {

    @Resource(name = "jobBatchDAO")
    private JobBatchDAO jobBatchDAO;

    @Transactional(readOnly = true)
    public List<JobBatch> listar(Long idApplicationBatch) {
        return jobBatchDAO.listar(idApplicationBatch);
    }

    @Transactional(readOnly = true)
    public JobBatch obtener(Long idJobBatch) {
        return jobBatchDAO.obtener(idJobBatch);
    }

    @Transactional
    public void insertar(JobBatch o) {
        jobBatchDAO.save(o);
    }

    @Transactional
    public void actualizar(JobBatch o) {
        jobBatchDAO.saveOrUpdate(o);
    }

    @Transactional
    public void eliminar(JobBatch o) {
        jobBatchDAO.delete(o);
    }
}
