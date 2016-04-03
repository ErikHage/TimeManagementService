package com.ehage.tms;

import java.util.Comparator;

import com.ehage.tms.model.Shift;

public class ShiftTimeInComparator implements Comparator<Shift> {

	@Override
	public int compare(Shift shift1, Shift shift2) {
		return shift1.getTimeIn().compareTo(shift2.getTimeIn());
	}

}
