package br.com.thelastsurvivor.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

	
	
	public static Date stringToDate(String dateString){

        Date date = null;  
        //try {
        	Calendar dateAchieved = Calendar.getInstance(new Locale("portuguese"));
        	
        	dateAchieved.set(Integer.parseInt(dateString.substring(6)),
        			Integer.parseInt(dateString.substring(3,5)),
        			Integer.parseInt(dateString.substring(0,2)));
        	return dateAchieved.getTime();
        	
            //DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            //date = (Date)formatter.parse(dateAchieved.getTime());  
        //} catch (ParseException e) {              
         //   return null;
        //}  
        //return date;
		
	}
	
	
	public static String DateToString(Date date1){
		
		Calendar date = Calendar.getInstance();
		date.setTime(date1);
		return date.get(Calendar.DAY_OF_MONTH)+"/" + (((date.get(Calendar.MONTH)+"").length()) == 1 ? ("0"+date.get(Calendar.MONTH)) : date.get(Calendar.MONTH))  +"/"+date.get(Calendar.YEAR);
		
	}
}
