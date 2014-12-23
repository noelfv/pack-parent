package com.bbva.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class StoreProcedureTasklet implements Tasklet {

//    private static final Logger LOG = Logger.getLogger(StoreProcedureTasklet.class);
//    private JobBatchService jobBatchService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        RepeatStatus result = RepeatStatus.CONTINUABLE;
//        Map<String, Object> params = chunkContext.getStepContext().getJobParameters();
//        StringBuilder queryBuilder = new StringBuilder();
//        String jndi = null;
//        Long idJob = 0L;
//        
//        Connection connection;
//        CallableStatement statement;
//
//        try {
//            jndi = params.get(ParameterType.BATCH_JNDI).toString();
//            idJob = Long.parseLong(params.get(ParameterType.BATCH_ID_JOB).toString());
//        } catch (Exception e) {
//            chunkContext.getStepContext().getStepExecution().setExitStatus(ExitStatus.COMPLETED);
//            contribution.setExitStatus(ExitStatus.FAILED);
//            result = RepeatStatus.FINISHED;
//        }
//
//        LOG.info("jndi: [" + jndi + "]");
//        LOG.info("idJob: [" + idJob + "]");
//
//        if (result.isContinuable()) {
//            connection = getConnection(jndi);
//            statement = connection.prepareCall(queryBuilder.toString());
//            JobBatch jobBatch = jobBatchService.obtener(idJob);
//            List<StepBatch> step = jobBatch.getSteps();
//
//            for (ParameterBatch p : step.get(0).getParameters()) {
//
//            }
//
//            // jobBatch.
//            try {
//                statement.close();
//            } catch (SQLException e) {
//                LOG.error("Statement close fail", e);
//            }
//
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                LOG.error("Connection close fail", e);
//            }
//        }

        return result;
    }

//    public void setJobBatchService(JobBatchService jobBatchService) {
//        this.jobBatchService = jobBatchService;
//    }

}
