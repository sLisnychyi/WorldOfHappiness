package com.happinest.world.service;

import java.util.Calendar;
import java.util.Date;

public class InternetServiceHelper {

    public static Date decrimentDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime ( date );
        int daysToDecrement = -7;
        cal.add(Calendar.DATE, daysToDecrement);
        return cal.getTime();
    }
}
