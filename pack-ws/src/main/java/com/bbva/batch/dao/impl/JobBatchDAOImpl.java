package com.bbva.batch.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bbva.batch.dao.JobBatchDAO;
import com.bbva.batch.domain.JobBatch;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("jobBatchDAO")
public class JobBatchDAOImpl extends HibernateDAO<JobBatch> implements JobBatchDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<JobBatch> listar(Long idApplicationBatch, boolean lazy) {
        Criteria criteria = super.getCriteria(JobBatch.class);
        criteria.add(Restrictions.eq("application.id", idApplicationBatch));
        criteria.addOrder(Order.asc("id"));
        return (List<JobBatch>) criteria.list();
    }

    @Override
    public JobBatch obtener(Long idJobBatch, boolean lazy) {
        Criteria criteria = super.getCriteria(JobBatch.class);
        criteria.add(Restrictions.eq("id", idJobBatch));
        return (JobBatch) criteria.uniqueResult();
    }

}
