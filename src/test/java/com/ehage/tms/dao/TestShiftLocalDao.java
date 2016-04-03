package com.ehage.tms.dao;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ehage.tms.helper.TMSTestHelper;
import com.ehage.tms.model.Shift;

public class TestShiftLocalDao {

	private ShiftLocalDao storage;
	
	@Before
	public void setUp() {
		storage = new ShiftLocalDao();
	}
	
	@After
	public void cleanUp() {
		storage.deleteAll();
	}
	
	@Test
	public void testCreate_GivenShift_ExpectOptionalShiftPresent() {
		Shift s1 = TMSTestHelper.getShift(1l, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0),
				LocalDateTime.of(2016, 10, 17, 12, 0));
		
		Optional<Shift> sopt1 = storage.create(s1);
		Shift sOut1 = sopt1.get();
		assertEquals(sOut1, s1);
	}

	@Test
	public void testReadAll_ExpectOptionalShiftList() {
		Shift s1 = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0),
				LocalDateTime.of(2016, 10, 17, 12, 0));
		storage.create(s1);		
		Shift s2 = TMSTestHelper.getShift(2L, "T1", 
				LocalDateTime.of(2016, 10, 17, 12, 30),
				LocalDateTime.of(2016, 10, 17, 5, 0));		
		storage.create(s2);
			
		assertEquals(storage.read(null).size(), 2);
	}
	
	@Test
	public void testReadAll_ExpectEmptyOptionalShiftList() {
		assertEquals(storage.read(null).size(), 0);
	}	
	
	@Test
	public void testReadByEmployeeId_GivenValidEmployeeId_ExpectOptionalShiftList() {
		Shift s1 = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0),
				LocalDateTime.of(2016, 10, 17, 12, 0));
		storage.create(s1);		
		Shift s2 = TMSTestHelper.getShift(2L, "T1", 
				LocalDateTime.of(2016, 10, 17, 12, 30),
				LocalDateTime.of(2016, 10, 17, 5, 0));		
		storage.create(s2);
		Shift s3 = TMSTestHelper.getShift(3L, "T2", 
				LocalDateTime.of(2016, 10, 17, 12, 30),
				LocalDateTime.of(2016, 10, 17, 5, 0));		
		storage.create(s3);
			
		assertEquals(storage.readByEmployeeId("T1").size(), 2);
		assertEquals(storage.readByEmployeeId("T2").size(), 1);
	}
	
	@Test
	public void testReadByEmployeeId_GivenInvalidEmployeeId_ExpectEmptyOptionalShiftList() {			
		assertEquals(storage.readByEmployeeId("T1").size(), 0);
	}	
	
	@Test
	public void testReadByShiftId_GivenValidShiftId_ExpectPresentOptionalShift() {
		Shift s1 = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0),
				LocalDateTime.of(2016, 10, 17, 12, 0));
		
		Optional<Shift> sOpt1 = storage.create(s1);
		Shift sOut1 = sOpt1.get();
		Optional<Shift> sOpt2 = storage.readByShiftId(sOut1.getShiftId());
		
		assertTrue(sOpt2.isPresent());
		assertEquals(sOut1, sOpt2.get());
	}
	
	@Test
	public void testReadByShiftId_GivenInvalidShiftId_ExpectEmptyOptionalShift() {			
		assertFalse(storage.readByShiftId(1L).isPresent());
	}	
	
	@Test
	public void testUpdate_GivenShift_ExpectUpdatedShift() {
		Shift s1 = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0),
				LocalDateTime.of(2016, 10, 17, 12, 0));
		Optional<Shift> sOut1 = storage.create(s1);
		
		Shift s2 = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 12, 30),
				LocalDateTime.of(2016, 10, 17, 5, 0));		
		Optional<Shift> sOut2 =  storage.update(s2);		
		
		assertTrue(sOut1.isPresent());
		assertTrue(sOut2.isPresent());
		assertEquals(sOut1.get().getTimeIn(), LocalDateTime.of(2016, 10, 17, 8, 0));
		assertEquals(sOut2.get().getTimeIn(), LocalDateTime.of(2016, 10, 17, 12, 30));
	}
	
	@Test
	public void testUpdate_GivenShift_ExpectEmptyOptionalShift() {
		Shift s1 = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0),
				LocalDateTime.of(2016, 10, 17, 12, 0));
		Optional<Shift> sOut1 = storage.update(s1);
		
		assertFalse(sOut1.isPresent());
	}
	
	@Test
	public void testDeleteById_GivenShiftId_ExpectReturnTrue() {
		Shift s1 = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0),
				LocalDateTime.of(2016, 10, 17, 12, 0));
		
		Optional<Shift> sOut1 = storage.create(s1);		
		long id = sOut1.get().getShiftId();
		
		assertTrue(storage.readByShiftId(id).isPresent());
		assertTrue(storage.delete(id));
		assertFalse(storage.readByShiftId(id).isPresent());		
	}
	
	@Test
	public void testDeleteById_GivenShiftId_ExpectReturnFalse() {		
		assertFalse(storage.delete(1L));
	}
	
}
