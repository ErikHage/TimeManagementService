package com.ehage.tms.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ehage.tms.dao.ShiftDao;
import com.ehage.tms.exception.PersistenceException;
import com.ehage.tms.helper.TMSTestHelper;
import com.ehage.tms.model.Shift;

public class TestShiftServiceImpl {

	@Mock
	private ShiftDao mockDao;
	
	@InjectMocks
	private ShiftServiceImpl shiftService;
	
	private Shift s1;
	private Shift s1u;
	private Shift s2;
	private Shift s3;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		s1 = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016,10,17,8,0),null);
		s1u = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016,10,17,8,0), 
				LocalDateTime.of(2016,10,17,4,0));
		
		s2 = TMSTestHelper.getShift(2L, "T1", 
				LocalDateTime.of(2016,10,17,12,30), 
				LocalDateTime.of(2016,10,17,5,0));
		
		s3 = TMSTestHelper.getShift(3L, "T2", 
				LocalDateTime.of(2016,10,17,8,0), 
				LocalDateTime.of(2016,10,17,4,0));
	}
	
	@Test
	public void testPunch_GivenEmployeeIdAndNoActiveShift_ExpectShiftWithNullTimeOut() {		
		when(mockDao.create(any(Shift.class))).thenReturn(Optional.of(s1));
		when(mockDao.readLatestByEmployeeId("T1")).thenReturn(Optional.empty());
		
		Shift sOut1 = shiftService.punch("T1");
		
		assertNotNull(sOut1.getTimeIn());
		assertNull(sOut1.getTimeOut());
		
		verify(mockDao, times(1)).readLatestByEmployeeId("T1");		
		verify(mockDao, times(1)).create(any(Shift.class));
		verifyNoMoreInteractions(mockDao);
	}
	
	@Test
	public void testPunch_GivenEmployeeIdAndLastShiftClosed_ExpectShiftWithNullTimeOut() {		
		when(mockDao.create(any(Shift.class))).thenReturn(Optional.of(s1));
		when(mockDao.readLatestByEmployeeId("T1")).thenReturn(Optional.of(s2));
		
		Shift sOut1 = shiftService.punch("T1");
		
		assertNotNull(sOut1.getTimeIn());
		assertNull(sOut1.getTimeOut());
		
		verify(mockDao, times(1)).readLatestByEmployeeId("T1");		
		verify(mockDao, times(1)).create(any(Shift.class));
		verifyNoMoreInteractions(mockDao);
	}	
	
	@Test
	public void testPunch_GivenEmployeeIdAndHasActiveShift_ExpectShiftWithNotNullTimeOut() {		
		when(mockDao.readLatestByEmployeeId("T1")).thenReturn(Optional.of(s1));
		
		when(mockDao.update(any(Shift.class))).thenReturn(Optional.of(s1u));
		
		Shift sOut1 = shiftService.punch("T1");
		
		assertNotNull(sOut1.getTimeIn());
		assertNotNull(sOut1.getTimeOut());
		
		verify(mockDao, times(1)).readLatestByEmployeeId("T1");		
		verify(mockDao, times(1)).update(any(Shift.class));
		verifyNoMoreInteractions(mockDao);
	}	

	@Test
	public void testCreateShift_GivenShift_ExpectShift() {
		Shift s1out = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.now(), null); 
		
		when(mockDao.create(s1)).thenReturn(Optional.of(s1out));
		
		Shift out = shiftService.createShift(s1);
		assertEquals(out, s1out);
		
		verify(mockDao, times(1)).create(s1);
		verifyNoMoreInteractions(mockDao);
	}

	@Test
	public void testReadByShiftId_GivenValidShiftId_ExpectShift() {
		when(mockDao.readByShiftId(1L)).thenReturn(Optional.of(s1));		
		Shift s1out = shiftService.readByShiftId(1L);		
		assertNotNull(s1out);	
		
		verify(mockDao, times(1)).readByShiftId(1L);		
		verifyNoMoreInteractions(mockDao);
	}
	
	@Test(expected=PersistenceException.class)
	public void testReadByShiftId_GivenValidShiftId_ExpectException() {
		when(mockDao.readByShiftId(1L)).thenReturn(Optional.empty());	
		shiftService.readByShiftId(1L);
		
		verify(mockDao, times(1)).readByShiftId(1L);		
		verifyNoMoreInteractions(mockDao);
	}

	@Test
	public void testReadByEmployeeId_GivenEmployeeIdWithTwoShifts_ReturnListOf2Shifts() {
		List<Shift> shifts = Arrays.asList(new Shift[] {s1, s2});
		
		when(mockDao.readByEmployeeId("T1")).thenReturn(shifts);
		
		List<Shift> shiftsOut = shiftService.readByEmployeeId("T1");
		
		assertEquals(2, shiftsOut.size());		
		verify(mockDao, times(1)).readByEmployeeId("T1");
		verifyNoMoreInteractions(mockDao);
	}
	
	@Test
	public void testReadByEmployeeId_GivenEmployeeIdWithNoShifts_ReturnListOf0Shifts() {
		List<Shift> shifts = new ArrayList<Shift>();
		
		when(mockDao.readByEmployeeId("T3")).thenReturn(shifts);
		
		List<Shift> shiftsOut = shiftService.readByEmployeeId("T3");
		
		assertEquals(0, shiftsOut.size());		
		verify(mockDao, times(1)).readByEmployeeId("T3");
		verifyNoMoreInteractions(mockDao);
	}

	@Test
	public void testRead_GivenEmptyStringQuery_ExpectListOf3Shifts() {
		List<Shift> shifts = Arrays.asList(new Shift[] {s1, s2, s3});
		
		when(mockDao.read("")).thenReturn(shifts);
		
		List<Shift> shiftsOut = shiftService.read("");
		
		assertEquals(3, shiftsOut.size());		
		verify(mockDao, times(1)).read("");
		verifyNoMoreInteractions(mockDao);
	}

	@Test
	public void testUpdateShift_GivenUpdatedExistingShift_ExpectShiftUpdated() {				
		when(mockDao.update(s2)).thenReturn(Optional.of(s2));
		
		Shift sOut = shiftService.updateShift(s2);
		
		assertEquals(sOut, s2);				
		verify(mockDao, times(1)).update(s2);
		verifyNoMoreInteractions(mockDao);
	}
	
	@Test(expected=PersistenceException.class)
	public void testUpdateShift_GivenNotExistingShift_ExpectException() {
		when(mockDao.update(s2)).thenThrow(new PersistenceException());
		
		shiftService.updateShift(s2);
		
		verify(mockDao, times(1)).update(s2);
		verifyNoMoreInteractions(mockDao);
	}

	@Test
	public void testDeleteShiftById_GivenValidShiftId_ExpectPass() {
		when(mockDao.delete(1L)).thenReturn(true);
		
		assertTrue(shiftService.deleteShiftById(1L));		
		
		verify(mockDao, times(1)).delete(1L);
		verifyNoMoreInteractions(mockDao);
	}
	
	@Test(expected=PersistenceException.class)
	public void testDeleteShiftById_GivenInvalidShiftId_ExpectException() {
		when(mockDao.delete(1L)).thenReturn(false);		
		
		shiftService.deleteShiftById(1L);	
		
		verify(mockDao, times(1)).delete(1L);
		verifyNoMoreInteractions(mockDao);
	}

}
