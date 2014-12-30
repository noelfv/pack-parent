package com.bbva.batch.service.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ApplicationBatchServiceImplTest.class,
    JobBatchServiceImplTest.class,
    StepBatchServiceImplTest.class,
    ParameterBatchServiceImplTest.class
})
public class AllTest {
}
