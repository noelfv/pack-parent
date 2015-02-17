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

    /***
    private void infoInvoke(ProceedingJoinPoint joinPoint, boolean lazy) {
        if (LOG.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n\tClass: ");
            sb.append(joinPoint.getTarget().getClass().getName());
            sb.append("\n\tMethod Invoke: ");
            sb.append(joinPoint.getSignature().getName());
            sb.append("\n\tArgs Length: ");
            sb.append(joinPoint.getArgs().length);
            sb.append("\n\tKind: ");
            sb.append(joinPoint.getKind());
            sb.append("\n\tLazy: ");
            sb.append((lazy ? "Use fetchProfile" : "No use fetchProfile"));
            LOG.info(sb.toString());
        }
    } ***/

    @Around("execution(* com.bbva.*.dao.impl.*.* (..)) && args(.., lazy)")
    public Object invoke(ProceedingJoinPoint joinPoint, boolean lazy) throws Exception {
        Object o = null;
        /*** infoInvoke(joinPoint, lazy); ***/

        HibernateDAO<?> dao = (HibernateDAO<?>) joinPoint.getTarget();
        if (lazy && !dao.getCurrentSession().isFetchProfileEnabled(FETCH_PROFILE)) {
            dao.getCurrentSession().enableFetchProfile(FETCH_PROFILE);
            LOG.info("enableFetchProfile: [" + FETCH_PROFILE + "]");
        }

        try {
            o = joinPoint.proceed();
        } catch (Throwable t) {
            throw new Exception("FetchProfileInterceptor:invoke(ProceedingJoinPoint joinPoint, boolean lazy)", t);
        } finally {
            if (lazy) {
                dao.getCurrentSession().disableFetchProfile(FETCH_PROFILE);
            }
        }

        return o;
    }
}
