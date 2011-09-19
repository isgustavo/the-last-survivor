package br.com.thelastsurvivor.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

	
	
	public static Date stringToDate(String dateString){

        Date date = null;  
        try {  
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            date = (Date)formatter.parse(dateString.substring(0,2)+"/"+dateString.substring(3,5)+"/"+dateString.substring(6));  
        } catch (ParseException e) {              
            return null;
        }  
        return date;
		
	}
}
