package com.bbva.quartz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.quartz.dao.TriggerDAO;
import com.bbva.quartz.domain.Trigger;
import com.bbva.quartz.service.TriggerService;

@Service("triggerService")
public class TriggerServiceImpl implements TriggerService {

    @Resource(name = "triggerDAO")
    private TriggerDAO triggerDAO;

    @Transactional(readOnly = true)
    @Override
    public List<Trigger> listar() {
        return triggerDAO.listar();
    }

}
