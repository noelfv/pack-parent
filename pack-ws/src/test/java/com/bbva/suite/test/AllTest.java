package com.bbva.suite.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bbva.batch.factory.impl.JobBatchFactoryImplTest;
import com.bbva.batch.service.impl.ApplicationBatchServiceImplTest;
import com.bbva.packws.service.impl.SolicitudServiceImplTest;
import com.bbva.quartz.factory.impl.QuartzFactoryImplTest;

@RunWith(Suite.class)
@SuiteClasses({
    SolicitudServiceImplTest.class,
    ApplicationBatchServiceImplTest.class,
    JobBatchFactoryImplTest.class,
    QuartzFactoryImplTest.class
})
public class AllTest { }