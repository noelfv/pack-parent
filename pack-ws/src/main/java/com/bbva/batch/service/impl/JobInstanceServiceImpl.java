package com.bbva.batch.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.packws.dao.JobInstanceDAO;
import com.bbva.batch.service.JobInstanceService;

@Service("jobInstanceService")
public class JobInstanceServiceImpl implements Serializable, JobInstanceService {

    private static final long serialVersionUID = 1L;

    @Resource(name = "jobInstanceDAO")
    private JobInstanceDAO jobInstanceDAO;

    @Transactional(readOnly = true)
    @Override
    public Long obtenerUltimaInstancia(String name) {
        return jobInstanceDAO.obtenerUltimaInstancia(name);
    }

}
