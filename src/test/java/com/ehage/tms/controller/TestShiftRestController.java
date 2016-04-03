package com.ehage.tms.controller;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*; 

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ehage.tms.TimeManagementServiceApplication;
import com.ehage.tms.config.Constants;
import com.ehage.tms.config.Routes;
import com.ehage.tms.exception.PersistenceException;
import com.ehage.tms.helper.ShiftHelper;
import com.ehage.tms.helper.TMSTestHelper;
import com.ehage.tms.model.Shift;
import com.ehage.tms.service.ShiftService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes 
		= {TimeManagementServiceApplication.class})
public class TestShiftRestController {

	@Mock
	private ShiftService mockShiftService;
	
	@InjectMocks
	private ShiftRestController controller;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(controller).build();
	}
	
	@Test
	public void testCreateShift_GivenShiftWithNullTimeOut_ExpectSuccess() throws Exception {
		Shift request = TMSTestHelper.getShift(null, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0), null);
		Shift response = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0), 
				null);
	
		String requestJson = ShiftHelper.getShiftJson(request);
		
		when(mockShiftService.createShift(request)).thenReturn(response);
		
		mockMvc.perform(post(Routes.SHIFT_CREATE)
				.contentType(Constants.APPLICATION_JSON)
				.content(requestJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(Constants.APPLICATION_JSON))
				.andExpect(jsonPath("employeeId", is("T1")))
				.andExpect(jsonPath("shiftId", is(1)))
				.andExpect(jsonPath("timeIn[0]", is(2016)))
				.andExpect(jsonPath("timeIn[1]", is(10)))
				.andExpect(jsonPath("timeIn[2]", is(17)))
				.andExpect(jsonPath("timeIn[3]", is(8)))
				.andExpect(jsonPath("timeIn[4]", is(0)))
				.andExpect(jsonPath("timeOut").value(IsNull.nullValue()))
				.andExpect(jsonPath("details").value(IsNull.nullValue()));
		
		verify(mockShiftService, times(1)).createShift(request);
		verifyNoMoreInteractions(mockShiftService);
	}
	
	@Test
	public void testCreateShift_GivenCompleteShift_ExpectSuccess() throws Exception {
		Shift request = TMSTestHelper.getShift(null, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0), null);
		Shift response = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0), 
				LocalDateTime.of(2016, 10, 17, 16, 0));
	
		String requestJson = ShiftHelper.getShiftJson(request);
		
		when(mockShiftService.createShift(request)).thenReturn(response);
		
		mockMvc.perform(post(Routes.SHIFT_CREATE)
				.contentType(Constants.APPLICATION_JSON)
				.content(requestJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(Constants.APPLICATION_JSON))
				.andExpect(jsonPath("employeeId", is("T1")))
				.andExpect(jsonPath("shiftId", is(1)))
				.andExpect(jsonPath("timeIn[0]", is(2016)))
				.andExpect(jsonPath("timeIn[1]", is(10)))
				.andExpect(jsonPath("timeIn[2]", is(17)))
				.andExpect(jsonPath("timeIn[3]", is(8)))
				.andExpect(jsonPath("timeIn[4]", is(0)))
				.andExpect(jsonPath("timeOut[0]", is(2016)))
				.andExpect(jsonPath("timeOut[1]", is(10)))
				.andExpect(jsonPath("timeOut[2]", is(17)))
				.andExpect(jsonPath("timeOut[3]", is(16)))
				.andExpect(jsonPath("timeOut[4]", is(0)))
				.andExpect(jsonPath("details").value(IsNull.nullValue()));
		
		verify(mockShiftService, times(1)).createShift(request);
		verifyNoMoreInteractions(mockShiftService);
	}
	
	@Test
	public void testReadByShiftId_GivenValidId_ExpectShiftInResponse() throws Exception {
		Shift response = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0), 
				LocalDateTime.of(2016, 10, 17, 16, 0));
		
		when(mockShiftService.readByShiftId(1L)).thenReturn(response);
		
		mockMvc.perform(get(Routes.SHIFT_READ_BY_ID.replace("{shiftId}", "1")))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(Constants.APPLICATION_JSON))
				.andExpect(jsonPath("employeeId", is("T1")))
				.andExpect(jsonPath("shiftId", is(1)))
				.andExpect(jsonPath("timeIn[0]", is(2016)))
				.andExpect(jsonPath("timeIn[1]", is(10)))
				.andExpect(jsonPath("timeIn[2]", is(17)))
				.andExpect(jsonPath("timeIn[3]", is(8)))
				.andExpect(jsonPath("timeIn[4]", is(0)))
				.andExpect(jsonPath("timeOut[0]", is(2016)))
				.andExpect(jsonPath("timeOut[1]", is(10)))
				.andExpect(jsonPath("timeOut[2]", is(17)))
				.andExpect(jsonPath("timeOut[3]", is(16)))
				.andExpect(jsonPath("timeOut[4]", is(0)))
				.andExpect(jsonPath("details").value(IsNull.nullValue()));
		
		verify(mockShiftService, times(1)).readByShiftId(1L);
		verifyNoMoreInteractions(mockShiftService);
	}
	
	@Test
	public void testReadByShiftId_GivenInvalidId_ExpectExceptionMessage() throws Exception {
		when(mockShiftService.readByShiftId(1L)).thenThrow(
				new PersistenceException("Exception message", "1"));
		
		mockMvc.perform(get(Routes.SHIFT_READ_BY_ID.replace("{shiftId}", "1")))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(Constants.APPLICATION_JSON))
				.andExpect(jsonPath("message", is("Exception message")))
				.andExpect(jsonPath("id", is("1")));
		
		verify(mockShiftService, times(1)).readByShiftId(1L);
		verifyNoMoreInteractions(mockShiftService);
	}
	
	@Test
	public void testReadByEmployeeId_GivenEmployeeId_ExpectListOfShifts() throws Exception {
		Shift s1 = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0), 
				LocalDateTime.of(2016, 10, 17, 16, 0));
		Shift s2 = TMSTestHelper.getShift(2L, "T1", 
				LocalDateTime.of(2016, 10, 18, 8, 0), 
				LocalDateTime.of(2016, 10, 18, 16, 0));
		Shift s3 = TMSTestHelper.getShift(3L, "T1", 
				LocalDateTime.of(2016, 10, 19, 8, 0), 
				LocalDateTime.of(2016, 10, 19, 16, 0));
		
		List<Shift> shifts = Arrays.asList(new Shift[] {s1,s2,s3});
		
		when(mockShiftService.readByEmployeeId("T1")).thenReturn(shifts);
		
		mockMvc.perform(get(Routes.SHIFT_READ_BY_EMPLOYEE.replace("{employeeId}", "T1")))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(Constants.APPLICATION_JSON))
				.andExpect(jsonPath("$",hasSize(3)));
		
		verify(mockShiftService, times(1)).readByEmployeeId("T1");
		verifyNoMoreInteractions(mockShiftService);
	}

	@Test
	public void testReadLatestByEmployeeId_GivenValidId_ExpectShift() throws Exception {
		Shift response = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0), 
				LocalDateTime.of(2016, 10, 17, 16, 0));
		
		when(mockShiftService.readLatestByEmployeeId("T1")).thenReturn(response);
		
		mockMvc.perform(get(Routes.SHIFT_READ_LATEST.replace("{employeeId}", "T1")))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(Constants.APPLICATION_JSON))
				.andExpect(jsonPath("employeeId", is("T1")))
				.andExpect(jsonPath("shiftId", is(1)))
				.andExpect(jsonPath("timeIn[0]", is(2016)))
				.andExpect(jsonPath("timeIn[1]", is(10)))
				.andExpect(jsonPath("timeIn[2]", is(17)))
				.andExpect(jsonPath("timeIn[3]", is(8)))
				.andExpect(jsonPath("timeIn[4]", is(0)))
				.andExpect(jsonPath("timeOut[0]", is(2016)))
				.andExpect(jsonPath("timeOut[1]", is(10)))
				.andExpect(jsonPath("timeOut[2]", is(17)))
				.andExpect(jsonPath("timeOut[3]", is(16)))
				.andExpect(jsonPath("timeOut[4]", is(0)))
				.andExpect(jsonPath("details").value(IsNull.nullValue()));
		
		verify(mockShiftService, times(1)).readLatestByEmployeeId("T1");
		verifyNoMoreInteractions(mockShiftService);
	}

	@Test
	public void testReadLatestByEmployeeId_GivenInvalidId_ExpectExceptionMessage() throws Exception {
		when(mockShiftService.readLatestByEmployeeId("T1")).thenThrow(
				new PersistenceException("Exception message", "T1"));
		
		mockMvc.perform(get(Routes.SHIFT_READ_LATEST.replace("{employeeId}", "T1")))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(Constants.APPLICATION_JSON))
				.andExpect(jsonPath("message", is("Exception message")))
				.andExpect(jsonPath("id", is("T1")));
		
		verify(mockShiftService, times(1)).readLatestByEmployeeId("T1");
		verifyNoMoreInteractions(mockShiftService);
	}
	
	@Test
	public void testReadAll_ExpectListOfShifts() throws Exception {
		Shift s1 = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0), 
				LocalDateTime.of(2016, 10, 17, 16, 0));
		Shift s2 = TMSTestHelper.getShift(2L, "T1", 
				LocalDateTime.of(2016, 10, 18, 8, 0), 
				LocalDateTime.of(2016, 10, 18, 16, 0));
		Shift s3 = TMSTestHelper.getShift(3L, "T2", 
				LocalDateTime.of(2016, 10, 19, 8, 0), 
				LocalDateTime.of(2016, 10, 19, 16, 0));
		
		List<Shift> shifts = Arrays.asList(new Shift[] {s1,s2,s3});
		
		when(mockShiftService.read("")).thenReturn(shifts);
		
		mockMvc.perform(get(Routes.SHIFT_READ_ALL))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(Constants.APPLICATION_JSON))
				.andExpect(jsonPath("$",hasSize(3)));
		
		verify(mockShiftService, times(1)).read("");
		verifyNoMoreInteractions(mockShiftService);
	}
	
	@Test
	public void testUpdateShift_GivenShift_ExpectSuccess() throws Exception {
		Shift request = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0),
				LocalDateTime.of(2016, 10, 17, 16, 0));
	
		String requestJson = ShiftHelper.getShiftJson(request);
		
		when(mockShiftService.updateShift(request)).thenReturn(request);
		
		mockMvc.perform(post(Routes.SHIFT_UPDATE)
				.contentType(Constants.APPLICATION_JSON)
				.content(requestJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(Constants.APPLICATION_JSON))
				.andExpect(jsonPath("employeeId", is("T1")))
				.andExpect(jsonPath("shiftId", is(1)))
				.andExpect(jsonPath("timeIn[0]", is(2016)))
				.andExpect(jsonPath("timeIn[1]", is(10)))
				.andExpect(jsonPath("timeIn[2]", is(17)))
				.andExpect(jsonPath("timeIn[3]", is(8)))
				.andExpect(jsonPath("timeIn[4]", is(0)))
				.andExpect(jsonPath("timeOut[0]", is(2016)))
				.andExpect(jsonPath("timeOut[1]", is(10)))
				.andExpect(jsonPath("timeOut[2]", is(17)))
				.andExpect(jsonPath("timeOut[3]", is(16)))
				.andExpect(jsonPath("timeOut[4]", is(0)))
				.andExpect(jsonPath("details").value(IsNull.nullValue()));
		
		verify(mockShiftService, times(1)).updateShift(request);
		verifyNoMoreInteractions(mockShiftService);
	}
	
	@Test
	public void testUpdateShift_GivenShift_ExpectException() throws Exception {
		Shift request = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016, 10, 17, 8, 0),
				LocalDateTime.of(2016, 10, 17, 16, 0));
		
		String requestJson = ShiftHelper.getShiftJson(request);
		
		when(mockShiftService.updateShift(request)).thenThrow(
				new PersistenceException("Exception message", "1"));
		
		mockMvc.perform(post(Routes.SHIFT_UPDATE)
				.contentType(Constants.APPLICATION_JSON)
				.content(requestJson))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(Constants.APPLICATION_JSON))
				.andExpect(jsonPath("message", is("Exception message")))
				.andExpect(jsonPath("id", is("1")));
		
		verify(mockShiftService, times(1)).updateShift(request);
		verifyNoMoreInteractions(mockShiftService);
	}
	
	@Test
	public void testDeleteByShiftId_GivenValidId_ExpectSuccess() throws Exception {
		mockMvc.perform(delete(Routes.SHIFT_DELETE.replace("{shiftId}", "1")))
				.andDo(print())
				.andExpect(status().isOk());
		
		verify(mockShiftService, times(1)).deleteShiftById(1L);;
		verifyNoMoreInteractions(mockShiftService);
		
	}
	
	@Test
	public void testDeleteByShiftId_GivenInvalidId_ExpectException() throws Exception {
		when(mockShiftService.deleteShiftById(1L)).thenThrow(
				new PersistenceException("Exception message", "1"));

		mockMvc.perform(delete(Routes.SHIFT_DELETE.replace("{shiftId}", "1")))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(Constants.APPLICATION_JSON))
				.andExpect(jsonPath("message", is("Exception message")))
				.andExpect(jsonPath("id", is("1")));

		verify(mockShiftService, times(1)).deleteShiftById(1L);;
		verifyNoMoreInteractions(mockShiftService);
	}
}
