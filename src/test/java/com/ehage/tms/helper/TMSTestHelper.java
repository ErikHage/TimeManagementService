package com.ehage.tms.helper;

import java.time.LocalDateTime;

import com.ehage.tms.model.Shift;

public class TMSTestHelper {

	public static Shift getShift(Long shiftId, String employeeId, 
			LocalDateTime timeIn, LocalDateTime timeOut) {
		
		Shift shift = new Shift();
		shift.setShiftId(shiftId);
		shift.setEmployeeId(employeeId);
		shift.setTimeIn(timeIn);
		shift.setTimeOut(timeOut);
				
		return shift;
	}
	
}
