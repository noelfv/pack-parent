package com.bbva.batch.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.bbva.batch.dao.ApplicationBatchDAO;
import com.bbva.batch.domain.ApplicationBatch;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("applicationBatchDAO")
public class ApplicationBatchDAOImpl extends HibernateDAO<ApplicationBatch> implements ApplicationBatchDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<ApplicationBatch> listar() {
        Criteria criteria = super.getCriteria(ApplicationBatch.class);
        criteria.setFetchMode("jobs", FetchMode.SELECT);
        criteria.addOrder(Order.asc("id"));
        return (List<ApplicationBatch>) criteria.list();
    }

}
