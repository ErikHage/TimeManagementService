package com.ehage.tms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehage.tms.dao.ShiftDao;
import com.ehage.tms.exception.PersistenceException;
import com.ehage.tms.helper.ShiftHelper;
import com.ehage.tms.model.Shift;

@Service
public class ShiftServiceImpl implements ShiftService {

	@Autowired
	private ShiftDao shiftDao;
	
	@Override
	public Shift punch(String employeeId) {		
		Shift newShift = null;
		
		Optional<Shift> optShift = shiftDao.readLatestByEmployeeId(employeeId);
		
		if(optShift.isPresent() && optShift.get().getTimeOut() == null) {			
			newShift = updateShift(optShift.get());
		} else {
			newShift = createShift(ShiftHelper.getNewShift(employeeId));
		}
		
		return newShift;
	}

	@Override
	public Shift createShift(Shift shift) {		
		Optional<Shift> optShift = shiftDao.create(shift);		
		return optShift.orElseThrow(() -> new PersistenceException("Unable to create new shift for employee " 
				+ shift.getEmployeeId(), shift.getEmployeeId()));
	}

	@Override
	public Shift readByShiftId(Long shiftId) {
		return shiftDao.readByShiftId(shiftId).orElseThrow(() -> new PersistenceException("No shift exists with id = " 
				+ shiftId, shiftId.toString()));
	}

	@Override
	public List<Shift> readByEmployeeId(String employeeId) {
		return shiftDao.readByEmployeeId(employeeId);
	}
	
	@Override
	public Shift readLatestByEmployeeId(String employeeId) {
		return shiftDao.readLatestByEmployeeId(employeeId)
				.orElseThrow(() -> new PersistenceException("No shifts exists for employee " 
						+ employeeId, employeeId));
	}

	@Override
	public List<Shift> read(String query) {
		return shiftDao.read(query);
	}

	@Override
	public Shift updateShift(Shift shift) {
		Optional<Shift> optShift = shiftDao.update(shift);	
		return optShift.orElseThrow(() -> new PersistenceException("Unable to update shift with id " 
				+ shift.getShiftId(), shift.getShiftId().toString()));
	}

	@Override
	public boolean deleteShiftById(Long shiftId) {
		if(shiftDao.delete(shiftId)) {
			return true;
		}
		throw new PersistenceException("No record found for shift with id = " + shiftId, shiftId.toString());
	}
	

}
