package com.lic.epgs.mst.exceptionhandling;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleResourceNotFound(final ResourceNotFoundException exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}
	
	@ExceptionHandler(AddressServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleException(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(CustomerTypeServiceException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleExceptionNotFound(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(QuotationTypeServiceException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleQuotationNotFoundExceptionResponse(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}
	
	@ExceptionHandler(ZoneServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionZone(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(LayerTypeServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionLayerType(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(LayerNameServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionLayerName(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(RiskCategoryServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionRiskCategory(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(RiskGroupServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionRiskGroup(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(PremiumAdjustmentTypeServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionPremium(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(AccountingFrequencyServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionAccounting(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(PortfolioTypeServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionPortfolio(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(FundNameServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionFundName(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(MergerTypeServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionMergerType(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(TypeOfSwitchServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionTypeSwitch(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(TypeOfLeaveServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionTypeLeave(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(LeaveEncashmentBenefitTypeServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionLeaveEncashmentFrequency(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(EncashmentFrequencyServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionEncashmentFrequency(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}
	@ExceptionHandler(DepartmentServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionDepartment(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(DesignationServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionDesignation(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(CityServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionCity(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}

	@ExceptionHandler(MedicalTestServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleExceptionMedicalTest(final Exception exception,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setCallUri(request.getRequestURI());

		return error;
	}
}
