package com.bbva.suite.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bbva.batch.service.impl.ApplicationBatchServiceImplTest;
import com.bbva.batch.service.impl.JobBatchServiceImplTest;
import com.bbva.batch.service.impl.ParameterBatchServiceImplTest;
import com.bbva.batch.service.impl.StepBatchServiceImplTest;

@RunWith(Suite.class)
@SuiteClasses({
    ApplicationBatchServiceImplTest.class,
    JobBatchServiceImplTest.class,
    StepBatchServiceImplTest.class,
    ParameterBatchServiceImplTest.class
})
public class AllTest { }