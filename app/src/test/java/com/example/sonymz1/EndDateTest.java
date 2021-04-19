package com.example.sonymz1;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class EndDateTest {
    @Test
    public void timeLeftTest()
    {
        Date now = new Date();
        long ms = now.getTime();
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, currentDay+1);
        int daysLeft = calendar.get(Calendar.DAY_OF_MONTH) - currentDay;
        assertEquals(daysLeft,1);
        ms += TimeUnit.DAYS.toMillis(daysLeft);
        EndDate endDate = new EndDate(ms);
        String expectedString = "00:23:59:59";
        assertEquals(endDate.getValue(),expectedString);
        System.out.println(endDate.getValue());
    }
}
