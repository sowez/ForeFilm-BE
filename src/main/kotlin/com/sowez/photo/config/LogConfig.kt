package com.sowez.photo.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor


inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!

@Component
class LogInterceptor: HandlerInterceptor {

    val log = logger()

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        log.info("[REQUEST] {} {}", request.method, request.requestURI)
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        log.info("[RESPONSE] {} {}\n\n", request.method, request.requestURI)
    }
}

@Component
@Aspect
class LogAspect {

    val log = logger()
    val MAX_LOG_LENGTH = 1000

    @Around("execution(* com.sowez.photo.controller.*Controller.*(..))")
    @Throws(Throwable::class)
    fun controllerLogging(jp: ProceedingJoinPoint): Any {
        log.info("* 메서드 " + jp.signature.name + " 시작")
        val parameters = getSubString(jp.args.contentToString())
        log.info("* 파라미터: $parameters")

        val result = jp.proceed()

        log.info("* 메서드 " + jp.signature.name + " 종료")
        val resultStr = getSubString(result.toString())
        log.info("* 결과: $resultStr")

        return result
    }

    private fun getSubString(str: String): String {
        return if (str.length <= MAX_LOG_LENGTH) {
            str
        } else {
            str.substring(MAX_LOG_LENGTH).plus("...")
        }
    }
}