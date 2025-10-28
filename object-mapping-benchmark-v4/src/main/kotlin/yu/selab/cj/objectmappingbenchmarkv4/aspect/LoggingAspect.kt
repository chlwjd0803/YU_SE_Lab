package yu.selab.cj.objectmappingbenchmarkv4.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import kotlin.jvm.java

@Aspect
@Component
class LoggingAspect {

    private val logger = LoggerFactory.getLogger(LoggingAspect::class.java)


    @Around("execution(* yu.selab.cj.objectmappingbenchmarkv4.user.service.*.*(..))")
    fun logPerformance(joinPoint : ProceedingJoinPoint) : Any? {
        val stop = StopWatch()
        stop.start()
        val result = joinPoint.proceed()
        stop.stop()
        logger.info("method: {}, took {} ms", joinPoint.signature.name, stop.totalTimeMillis)
        return result
    }


}