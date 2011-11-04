package br.com.thelastsurvivor.util;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	
	
	public static Date stringToDate(String dateString){

        Date date = null;  
        //try {
        	Calendar dateAchieved = Calendar.getInstance();
        	
        	dateAchieved.set(Integer.parseInt(dateString.substring(6)),
        			Integer.parseInt(dateString.substring(3,5)),
        			Integer.parseInt(dateString.substring(0,2)));
        	
        	
        	//Locale locale = new Locale("ja");
        	//DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        	
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
		return (((date.get(Calendar.DAY_OF_MONTH)+"").length()) == 1 ? ("0"+date.get(Calendar.DAY_OF_MONTH)) : date.get(Calendar.DAY_OF_MONTH))+ 
					"/" + (((date.get(Calendar.MONTH)+"").length()) == 1 ? ("0"+date.get(Calendar.MONTH)) : date.get(Calendar.MONTH))+  
					"/" +  date.get(Calendar.YEAR);
		
	}
}
