package com.bbva.quartz.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.bbva.quartz.dao.TriggerDAO;
import com.bbva.quartz.domain.Trigger;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("triggerDAO")
public class TriggerDAOImpl extends HibernateDAO<Trigger> implements Serializable, TriggerDAO {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Trigger> listar() {
		Criteria criterioTrigger = super.getCriteria(Trigger.class);
		criterioTrigger.addOrder(Order.asc("triggerGroup"));
		criterioTrigger.addOrder(Order.asc("triggerName"));
		return (List<Trigger>) criterioTrigger.list();
	}
}
