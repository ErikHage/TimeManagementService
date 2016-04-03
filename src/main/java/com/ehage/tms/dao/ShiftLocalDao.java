package com.ehage.tms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ehage.tms.ShiftTimeInComparator;
import com.ehage.tms.model.Shift;

@Repository
public class ShiftLocalDao implements ShiftDao {

	private static final Map<Long, Shift> shiftMap
		= new HashMap<Long, Shift>();
	
	private static final AtomicLong shiftIdSeq = new AtomicLong(1);
	
	@Override
	public Optional<Shift> create(Shift shift) {
		shift.setShiftId(shiftIdSeq.getAndIncrement());
		shiftMap.put(shift.getShiftId(), shift);
		return Optional.of(shiftMap.get(shift.getShiftId()));
	}
	
	@Override
	public List<Shift> read(String query) {
		return shiftMap.values()
				.parallelStream()
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Shift> readByShiftId(Long shiftId) {
		return shiftMap.values()
				.parallelStream()
				.filter(shift -> shift.getShiftId() == shiftId)
				.findAny();
	}

	@Override
	public List<Shift> readByEmployeeId(String employeeId) {
		return shiftMap.values()
				.parallelStream()
				.filter(shift -> shift.getEmployeeId() == employeeId)
				.collect(Collectors.toList());
	}
	
	@Override
	public Optional<Shift> readLatestByEmployeeId(String employeeId) {
		return readByEmployeeId(employeeId)
				.parallelStream()
				.sorted(new ShiftTimeInComparator())
				.findFirst();				
	}

	@Override
	public Optional<Shift> update(Shift shift) {
		if(shiftMap.containsKey(shift.getShiftId())) {
			shiftMap.put(shift.getShiftId(), shift);
			return Optional.of(shiftMap.get(shift.getShiftId()));
		}		
		return Optional.empty();
	}

	@Override
	public boolean delete(Long shiftId) {
		if(shiftMap.containsKey(shiftId)) {
			shiftMap.remove(shiftId);
			return true;
		} 
		return false;
	}
	
	protected void deleteAll() {
		shiftMap.clear();
	}

}
