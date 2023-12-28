package com.lic.epgs.mst.exceptionhandling;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorMessage> somthingWentWrong(Exception ex)
	{
		ErrorMessage exceptionResponse = 
				new ErrorMessage(new Date(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,"Details not present in DB");
		
		return new ResponseEntity<ErrorMessage>(exceptionResponse,
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	public class ErrorMessage 
	{
		
		//@CreatedDate
		private Date timeStamp;
		private String errorMessage;
		private HttpStatus errorCode;
		private String description;
		
		public ErrorMessage() {
			
		}
		
		public ErrorMessage(Date timeStamp,String errorMessage, HttpStatus errorCode, String description) {
			super();
			this.timeStamp = timeStamp;
			this.errorMessage = errorMessage;
			this.errorCode = errorCode;
			this.description = description;
		}

		
		public String getErrorMessage() {
			return errorMessage;
		}

		
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		
		public HttpStatus getErrorCode() {
			return errorCode;
		}

		
		public void setErrorCode(HttpStatus errorCode) {
			this.errorCode = errorCode;
		}

		
		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		
		public Date getTimeStamp() {
			return timeStamp;
		}

		
		public void setTimeStamp(Date timeStamp) {
			this.timeStamp = timeStamp;
		}
	}

}
