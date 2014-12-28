package com.bbva.batch.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.everis.core.dao.impl.HibernateDAO;

@Aspect
public class FetchProfileInterceptor {

    private static final Logger LOG = Logger.getLogger(FetchProfileInterceptor.class);
    private static final String FETCH_PROFILE = "fetchProfile";

    @Around("execution(* com.bbva.*.dao.impl.*.* (..)) && args(.., lazy)")
    public Object invoke(ProceedingJoinPoint joinPoint, boolean lazy) throws Exception {
        Object o = null;
        LOG.info("Class: " + joinPoint.getTarget().getClass().getName());
        LOG.info("Method Invoke: " + joinPoint.getSignature().getName());
        LOG.info("Args Length: " + joinPoint.getArgs().length);
        LOG.info("Kind: " + joinPoint.getKind());
        LOG.info("Lazy: " + (lazy ? "Use fetchProfile" : "No use fetchProfile"));

        HibernateDAO<?> dao = (HibernateDAO<?>) joinPoint.getTarget();
        if (lazy && !dao.getCurrentSession().isFetchProfileEnabled(FETCH_PROFILE)) {
            dao.getCurrentSession().enableFetchProfile(FETCH_PROFILE);
            LOG.info("enableFetchProfile: [" + FETCH_PROFILE + "]");
        }

        try {
            o = joinPoint.proceed();
        } catch (Throwable t) {
            throw new Exception("FetchProfileInterceptor:invoke(ProceedingJoinPoint joinPoint, boolean lazy)", t);
        }

        return o;
    }
}
