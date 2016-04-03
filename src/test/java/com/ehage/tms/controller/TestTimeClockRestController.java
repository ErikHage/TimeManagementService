package com.ehage.tms.controller;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*; 

import java.time.LocalDateTime;

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
import com.ehage.tms.helper.TMSTestHelper;
import com.ehage.tms.model.Shift;
import com.ehage.tms.service.ShiftService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes 
		= {TimeManagementServiceApplication.class})
public class TestTimeClockRestController {

	@Mock
	private ShiftService mockShiftService;
	
	@InjectMocks
	private TimeClockRestController controller;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(controller).build();
	}
	
	@Test
	public void testPunchInOrOut_GivenEmployeeId_ExpectShiftWithNullTimeOut() throws Exception {
		Shift shift = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016,10,17,8,0), 
				null);	
		
		when(mockShiftService.punch("T1")).thenReturn(shift);
		
		mockMvc.perform(post(Routes.PUNCH_IN_OUT.replace("{employeeId}", "T1")))
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
			.andExpect(jsonPath("details").value(IsNull.nullValue()))
			.andDo(print());
				
		verify(mockShiftService, times(1)).punch("T1");
		verifyNoMoreInteractions(mockShiftService);		
	}
	
	@Test
	public void testPunchInOrOut_GivenEmployeeId_ExpectShiftWithNotNullTimeOut() throws Exception {
		Shift shift = TMSTestHelper.getShift(1L, "T1", 
				LocalDateTime.of(2016,10,17,8,0), 
				LocalDateTime.of(2016,10,17,16,0));			
		
		when(mockShiftService.punch("T1")).thenReturn(shift);
		
		mockMvc.perform(post(Routes.PUNCH_IN_OUT.replace("{employeeId}", "T1")))
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
			.andExpect(jsonPath("details").value(IsNull.nullValue()))
			.andDo(print());
				
		verify(mockShiftService, times(1)).punch("T1");
		verifyNoMoreInteractions(mockShiftService);		
	}

}
