package com.ehage.tms.dao;

import java.util.List;
import java.util.Optional;

import com.ehage.tms.model.Shift;

public interface ShiftDao {

	public Optional<Shift> create(Shift shift);
	
	public Optional<Shift> readByShiftId(Long shiftId);
	public Optional<Shift> readLatestByEmployeeId(String employeeId);
	public List<Shift> readByEmployeeId(String employeeId);
	public List<Shift> read(String query);
	
	public Optional<Shift> update(Shift shift);
	
	public boolean delete(Long shiftId);
	
}
