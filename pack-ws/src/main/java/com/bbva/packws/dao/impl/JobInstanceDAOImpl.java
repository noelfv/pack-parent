package com.bbva.packws.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.batch.core.JobInstance;
import org.springframework.stereotype.Repository;

import com.bbva.packws.dao.JobInstanceDAO;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("jobInstanceDAO")
public class JobInstanceDAOImpl extends HibernateDAO<JobInstance> implements JobInstanceDAO {

	@SuppressWarnings("unchecked")
	@Override
	public Long obtenerUltimaInstancia() {
		SQLQuery query = getSession().createSQLQuery("SELECT MAX(JOB_INSTANCE_ID) JOB_INSTANCE_ID FROM CONELE.BATCH_JOB_INSTANCE");
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		Map<String, Object> o = (HashMap<String, Object>) query.uniqueResult();
		return ((BigDecimal) o.get("JOB_INSTANCE_ID")).longValue();
	}

}