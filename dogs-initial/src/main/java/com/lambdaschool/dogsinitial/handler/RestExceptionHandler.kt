package com.lambdaschool.dogsinitial.handler

import com.lambdaschool.dogsinitial.exception.ResourceNotFoundException
import org.springframework.beans.TypeMismatchException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    @Autowired
    private val messageSource: MessageSource? = null

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(rnfe: ResourceNotFoundException, request: HttpServletRequest): ResponseEntity<*> {
        val errorDetail = ErrorDetail()
        errorDetail.setTimestamp(Date().time)
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value())
        errorDetail.setTitle("Resource Not Found")
        errorDetail.setDetail(rnfe.getMessage())
        errorDetail.setDeveloperMessage(rnfe.getClass().getName())

        return ResponseEntity<Any>(errorDetail, null, HttpStatus.NOT_FOUND)
    }


    override fun handleTypeMismatch(ex: TypeMismatchException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val errorDetail = ErrorDetail()
        errorDetail.setTimestamp(Date().time)
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value())
        errorDetail.setTitle(ex.propertyName)
        errorDetail.setDetail(ex.message)
        errorDetail.setDeveloperMessage(request.getDescription(true))

        return ResponseEntity(errorDetail, null, HttpStatus.NOT_FOUND)
    }

    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val errorDetail = ErrorDetail()
        errorDetail.setTimestamp(Date().time)
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value())
        errorDetail.setTitle(ex.requestURL)
        errorDetail.setDetail(request.getDescription(true))
        errorDetail.setDeveloperMessage("Rest Handler Not Found (check for valid URI)")

        return ResponseEntity(errorDetail, null, HttpStatus.NOT_FOUND)
    }
}
