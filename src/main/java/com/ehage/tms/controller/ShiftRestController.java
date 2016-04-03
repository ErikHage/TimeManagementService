package com.ehage.tms.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
public class ShiftRestController {

	private static final Log logger = LogFactory.getLog(ShiftRestController.class);
	
	@Autowired
	private ShiftService shiftService;
	
	//CREATE methods
	@RequestMapping(value = Routes.SHIFT_CREATE,
			consumes=Constants.APPLICATION_JSON,
			produces=Constants.APPLICATION_JSON,
			method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public Shift createShift(@RequestBody Shift shift) {
		logger.debug("endpoint: ..." + Routes.SHIFT_CREATE);
		return shiftService.createShift(shift);
	}
	
	//READ methods
	@RequestMapping(value = Routes.SHIFT_READ_BY_ID,
			produces=Constants.APPLICATION_JSON,
			method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Shift readByShiftId(@PathVariable String shiftId) {
		logger.debug("endpoint: ..." + Routes.SHIFT_READ_BY_ID.replace("{shiftId}", shiftId));
		return shiftService.readByShiftId(Long.parseLong(shiftId));
	}
	
	@RequestMapping(value = Routes.SHIFT_READ_BY_EMPLOYEE,
			produces=Constants.APPLICATION_JSON,
			method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Shift> readByEmployeeId(@PathVariable String employeeId) {
		logger.debug("endpoint: ..." + Routes.SHIFT_READ_BY_EMPLOYEE.replace("{employeeId}", employeeId));
		return shiftService.readByEmployeeId(employeeId);
	}
	
	@RequestMapping(value = Routes.SHIFT_READ_LATEST,
			produces=Constants.APPLICATION_JSON,
			method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Shift readLatestByEmployeeId(@PathVariable String employeeId) {
		logger.debug("endpoint: ..." + Routes.SHIFT_READ_LATEST.replace("{employeeId}", employeeId));
		return shiftService.readLatestByEmployeeId(employeeId);
	}
	
	@RequestMapping(value = Routes.SHIFT_READ_ALL,
			produces=Constants.APPLICATION_JSON,
			method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Shift> readByEmployeeId() {
		logger.debug("endpoint: ..." + Routes.SHIFT_READ_ALL);
		return shiftService.read("");
	}
	
	//UPDATE methods
	@RequestMapping(value = Routes.SHIFT_UPDATE,
			consumes=Constants.APPLICATION_JSON,
			produces=Constants.APPLICATION_JSON,
			method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public Shift updateShift(@RequestBody Shift shift) {
		logger.debug("endpoint: ..." + Routes.SHIFT_UPDATE);
		return shiftService.updateShift(shift);
	}
	
	//DELETE methods
	@RequestMapping(value = Routes.SHIFT_DELETE,
			produces=Constants.APPLICATION_JSON,
			method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteByShiftId(@PathVariable String shiftId) {
		logger.debug("endpoint: ..." + Routes.SHIFT_DELETE.replace("{shiftId}", shiftId));
		shiftService.deleteShiftById(Long.parseLong(shiftId));
	}	
	
	@ExceptionHandler(PersistenceException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public RestExceptionMessage handle(PersistenceException ex) {		
		RestExceptionMessage message = new RestExceptionMessage(ex.getMessage(), ex.getId());		
		return message;
	}
	
}
