package com.ehage.tms.helper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

import com.ehage.tms.model.Shift;

public class ShiftHelper {

	public static Shift getNewShift(String employeeId) {

		Shift shift = new Shift();

		shift.setEmployeeId(employeeId);
		shift.setTimeIn(LocalDateTime.now());

		return shift;
	}

	public static Shift punchOutShift(Shift shift) {		
		shift.setTimeOut(LocalDateTime.now());
		return shift;		
	}

	public static String getShiftJson(Shift s) {
		String jsonTemplate = getFile("json/shift.json");
		String json = String.format(jsonTemplate, 
				s.getShiftId(),
				s.getEmployeeId(),
				localDateToJsonFormat(s.getTimeIn()),
				localDateToJsonFormat(s.getTimeOut()),
				s.getDetails());		

		return json;
	}

	public static String localDateToJsonFormat(LocalDateTime dt) {
		StringBuilder sb = new StringBuilder();
		if(dt != null) {
			sb.append("[");
			sb.append(dt.getYear() + ",");
			sb.append(dt.getMonthValue() + ",");
			sb.append(dt.getDayOfMonth() + ",");
			sb.append(dt.getHour() + ",");
			sb.append(dt.getMinute() + ",");
			sb.append(dt.getSecond() + ",");
			sb.append(dt.getNano());
			sb.append("]");
		} else {
			sb.append("null");
		}
		
		return sb.toString();
	}	

	public static LocalDateTime jsonFormatToLocalDateTime(String json) {
		String[] items = json.split(",");
		LocalDateTime dt = LocalDateTime
				.of(Integer.parseInt(items[0]), 
						Integer.parseInt(items[1]), 
						Integer.parseInt(items[2]), 
						Integer.parseInt(items[3]), 
						Integer.parseInt(items[4]), 
						Integer.parseInt(items[5]), 
						Integer.parseInt(items[6]));		
		return dt;
	}

	public static String getFile(String filename) {
		StringBuilder result = new StringBuilder();		
		ClassLoader classLoader = ShiftHelper.class.getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}		

		return result.toString();
	}

}
