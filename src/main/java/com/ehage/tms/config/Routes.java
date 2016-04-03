package com.ehage.tms.config;

public abstract class Routes {

	public static final String MAIN_PAGE = "/";
	
	/* ShiftRestController */
	
	public static final String SHIFT_CREATE 		  = "/api/tms/shift/create";
	public static final String SHIFT_READ_BY_ID 	  = "/api/tms/shift/read/detail/{shiftId}";
	public static final String SHIFT_READ_BY_EMPLOYEE = "/api/tms/shift/read/{employeeId}";
	public static final String SHIFT_READ_LATEST 	  = "/api/tms/shift/read/latest/{employeeId}";
	public static final String SHIFT_READ_ALL 		  = "/api/tms/shift/read/all";
	public static final String SHIFT_UPDATE 		  = "/api/tms/shift/update";
	public static final String SHIFT_DELETE 		  = "/api/tms/shift/delete/{shiftId}";
	
	/* TimeClockRestController */
	public static final String PUNCH_IN_OUT 		  = "/api/tms/punch/{employeeId}";
	
	
}

