package com.bbva.suite.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bbva.batch.service.impl.ApplicationBatchServiceImplTest;
import com.bbva.batch.service.impl.JobBatchServiceImplTest;
import com.bbva.batch.service.impl.ParameterBatchServiceImplTest;
import com.bbva.batch.service.impl.StepBatchServiceImplTest;
import com.bbva.packws.service.impl.SolicitudServiceImplTest;
import com.bbva.quartz.factory.impl.QuartzFactoryImplTest;

@RunWith(Suite.class)
@SuiteClasses({
    SolicitudServiceImplTest.class,
    ApplicationBatchServiceImplTest.class,
    JobBatchServiceImplTest.class,
    StepBatchServiceImplTest.class,
    ParameterBatchServiceImplTest.class,
    JobBatchServiceImplTest.class,
    QuartzFactoryImplTest.class
})
public class AllTest { }