package com.bbva.quartz.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
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
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT X.TRIGGER_NAME triggerName");
        sb.append(", X.TRIGGER_GROUP triggerGroup");
        sb.append(", X.JOB_NAME jobName");
        sb.append(", X.JOB_GROUP jobGroup");
        sb.append(", X.NEXT_FIRE_TIME nextFireTime");
        sb.append(", X.TRIGGER_STATE triggerState");
        sb.append(", X.TRIGGER_TYPE triggerType");
        sb.append(", X.START_TIME startTime");
        sb.append(", X.END_TIME endTime");
        sb.append(", Z.EXIT_CODE exitCode");
        sb.append(" FROM CONELE.QRTZ_TRIGGERS X"); 
        sb.append(" LEFT JOIN(");
        sb.append(" SELECT A.JOB_NAME, MAX(A.JOB_INSTANCE_ID) JOB_INSTANCE_ID FROM CONELE.BATCH_JOB_INSTANCE A");
        sb.append(" GROUP BY A.JOB_NAME");
        sb.append(") Y ON X.JOB_NAME=Y.JOB_NAME");
        sb.append(" LEFT JOIN CONELE.BATCH_JOB_EXECUTION Z ON Y.JOB_INSTANCE_ID=Z.JOB_INSTANCE_ID");
        
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query
            .addScalar("triggerName", StandardBasicTypes.STRING)
            .addScalar("triggerGroup", StandardBasicTypes.STRING)
            .addScalar("jobName", StandardBasicTypes.STRING)
            .addScalar("jobGroup", StandardBasicTypes.STRING)
            .addScalar("nextFireTime", StandardBasicTypes.LONG)
            .addScalar("triggerState", StandardBasicTypes.STRING)
            .addScalar("triggerType", StandardBasicTypes.STRING)
            .addScalar("startTime", StandardBasicTypes.LONG)
            .addScalar("endTime", StandardBasicTypes.LONG)
            .addScalar("exitCode", StandardBasicTypes.STRING)
            .setResultTransformer(Transformers.aliasToBean(Trigger.class));
        return (List<Trigger>) query.list();
    }
}
