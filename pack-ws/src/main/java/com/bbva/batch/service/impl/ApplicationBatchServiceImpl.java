package com.bbva.batch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.batch.dao.ApplicationBatchDAO;
import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.service.ApplicationBatchService;

@Service("applicationBatchService")
public class ApplicationBatchServiceImpl implements ApplicationBatchService {

    @Resource(name = "applicationBatchDAO")
    private ApplicationBatchDAO applicationBatchDAO;

    public List<ApplicationBatch> listar() {
        return listar(false);
    }
    
    @Transactional(readOnly = true)
    public List<ApplicationBatch> listar(boolean lazy) {
        return applicationBatchDAO.listar(lazy);
    }

    @Transactional(readOnly = true)
	public ApplicationBatch obtener(Long idApplication) {
		return obtener(idApplication, false);
	}

    @Transactional(readOnly = true)
	public ApplicationBatch obtener(Long idApplication, boolean lazy) {
		return applicationBatchDAO.obtener(idApplication, lazy);
	}

	@Transactional
    public void insertar(ApplicationBatch o) {
        applicationBatchDAO.save(o);
    }

    @Transactional
    public void actualizar(ApplicationBatch o) {
        applicationBatchDAO.saveOrUpdate(o);
    }

    @Transactional
    public void eliminar(ApplicationBatch o) {
        applicationBatchDAO.delete(o);
    }
}
