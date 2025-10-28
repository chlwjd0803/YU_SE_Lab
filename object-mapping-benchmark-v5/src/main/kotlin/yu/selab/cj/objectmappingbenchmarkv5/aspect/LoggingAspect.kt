package yu.selab.cj.objectmappingbenchmarkv5.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Aspect
@Component
class LoggingAspect {

    private val logger = org.slf4j.LoggerFactory.getLogger(LoggingAspect::class.java)

    @Around("execution(* yu.selab.cj.objectmappingbenchmarkv5.user.service.*.*(..))")
    fun logUserServiceMethod(joinPoint : ProceedingJoinPoint) : Any? {
        val stop = StopWatch()
        stop.start()
        val result = joinPoint.proceed()
        stop.stop()
        logger.info("method: {}, took {} ms", joinPoint.signature.name, stop.totalTimeMillis)
        return result
    }


}