package com.ehage.tms.service;

import java.util.List;

import com.ehage.tms.model.Shift;

public interface ShiftService {

	public Shift punch(String employeeId);
	public Shift createShift(Shift shift);
	
	public Shift readByShiftId(Long shiftId);
	public Shift readLatestByEmployeeId(String employeeId);
	public List<Shift> readByEmployeeId(String employeeId);
	public List<Shift> read(String query);
	
	public Shift updateShift(Shift shift);
	
	public boolean deleteShiftById(Long shiftId);
}
