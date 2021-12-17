package com.company.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    //todo должен ли main controller заниматься конвертациями даты?
    public static Date convertDate(String date) throws ParseException {
        //todo magic numbers

        date = date.replace('T', ' ');
        //todo magic numbers
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date docDate= format.parse(date);
        return docDate;
    }
}
