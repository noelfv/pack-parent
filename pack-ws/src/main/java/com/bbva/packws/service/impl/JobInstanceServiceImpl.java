package com.bbva.packws.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbva.packws.dao.JobInstanceDAO;
import com.bbva.packws.service.JobInstanceService;

@Service("jobInstanceService")
public class JobInstanceServiceImpl implements Serializable, JobInstanceService {

	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jobInstanceDAO")
	private JobInstanceDAO jobInstanceDAO;
	
	@Override
	public Long obtenerUltimaInstancia() {
		return jobInstanceDAO.obtenerUltimaInstancia();
	}

}
