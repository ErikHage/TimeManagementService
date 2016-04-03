package com.ehage.tms.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ehage.tms.config.Constants;
import com.ehage.tms.config.Routes;
import com.ehage.tms.exception.PersistenceException;
import com.ehage.tms.exception.RestExceptionMessage;
import com.ehage.tms.model.Shift;
import com.ehage.tms.service.ShiftService;

@RestController
public class TimeClockRestController {
	
	private static final Log logger = LogFactory.getLog(TimeClockRestController.class);
	
	@Autowired
	private ShiftService shiftService;
	
	@RequestMapping(value = Routes.PUNCH_IN_OUT,
			produces=Constants.APPLICATION_JSON,
			method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public Shift punchInOrOut(@PathVariable String employeeId) {
		logger.debug("endpoint: ..." + Routes.PUNCH_IN_OUT.replace("{employeeId}", employeeId));
		return shiftService.punch(employeeId);
	}
	
	@ExceptionHandler(PersistenceException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public RestExceptionMessage handle(PersistenceException ex) {		
		RestExceptionMessage message = new RestExceptionMessage(ex.getMessage(), ex.getId());		
		return message;
	}
	
}
