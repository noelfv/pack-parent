package com.bbva.batch.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bbva.batch.dao.ApplicationBatchDAO;
import com.bbva.batch.domain.ApplicationBatch;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("applicationBatchDAO")
public class ApplicationBatchDAOImpl extends HibernateDAO<ApplicationBatch> implements ApplicationBatchDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<ApplicationBatch> listar(String name, boolean lazy) {
        Criteria criteria = super.getCriteria(ApplicationBatch.class);
        if(name != null && !name.trim().isEmpty()) {
            criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE).ignoreCase());
        }
        criteria.addOrder(Order.asc("id"));
        return (List<ApplicationBatch>) criteria.list();
    }

    @Override
    public ApplicationBatch obtener(String name, boolean lazy) {
        Criteria criteria = super.getCriteria(ApplicationBatch.class);
        criteria.add(Restrictions.eq("name", name));
        return (ApplicationBatch) criteria.uniqueResult();
    }

    @Override
    public ApplicationBatch obtener(Long idApplication, boolean lazy) {
        Criteria criteria = super.getCriteria(ApplicationBatch.class);
        criteria.add(Restrictions.eq("id", idApplication));
        return (ApplicationBatch) criteria.uniqueResult();
    }
}
