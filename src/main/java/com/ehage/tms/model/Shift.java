package com.ehage.tms.model;

import java.time.LocalDateTime;

/**
 * Store the information for one shift of an employee
 *  
 * @author Erik Hage 10/5/2013
 *
 */
public class Shift {

	private Long shiftId;
	private String employeeId;
	private LocalDateTime timeIn;
	private LocalDateTime timeOut;
	private String details;
	
	public Shift() {		
	}
	
	public Shift(String employeeID, LocalDateTime timeIn)
	{
		this.employeeId = employeeID;
		this.timeIn  = timeIn;
		this.timeOut = null;
	}
	
	public Shift(String employeeID, LocalDateTime timeIn, LocalDateTime timeOut)
	{
		this.employeeId = employeeID;
		this.timeIn  = timeIn;
		this.timeOut = timeOut;
	}
	
	public Shift(Long shiftID, String employeeID, LocalDateTime timeIn, LocalDateTime timeOut)
	{
		this.shiftId = shiftID;
		this.employeeId = employeeID;
		this.timeIn  = timeIn;
		this.timeOut = timeOut;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((shiftId == null) ? 0 : shiftId.hashCode());
		result = prime * result + ((timeIn == null) ? 0 : timeIn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shift other = (Shift) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (shiftId == null) {
			if (other.shiftId != null)
				return false;
		} else if (!shiftId.equals(other.shiftId))
			return false;
		if (timeIn == null) {
			if (other.timeIn != null)
				return false;
		} else if (!timeIn.equals(other.timeIn))
			return false;
		return true;
	}

	public String getEmployeeId() 
	{
		return employeeId;
	}

	public LocalDateTime getTimeIn() 
	{
		return timeIn;
	}

	public LocalDateTime getTimeOut() 
	{
		return timeOut;
	}
	
	public Long getShiftId() 
	{
		return shiftId;
	}
	
	public String getDetails()
	{
		return this.details;
	}

	public void setEmployeeId(String employeeId) 
	{
		this.employeeId = employeeId;
	}

	public void setTimeIn(LocalDateTime timeIn) 
	{
		this.timeIn = timeIn;
	}

	public void setTimeOut(LocalDateTime timeOut) 
	{
		this.timeOut = timeOut;
	}

	public void setShiftId(Long shiftId) 
	{
		this.shiftId = shiftId;
	}

	public void setDetails(String details)
	{
		this.details = details;
	}
	
}
