package utils.parser

import java.util.Date;
import org.codehaus.groovy.grails.web.json.JSONObject

import personal.alumno.Alumno;

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date;

public class Parser {
	static Date parseStringToDate(date){
		DateFormat outformat= new SimpleDateFormat("dd-MM-yyyy");
		Date d = outformat.parse(date)
		return d
	}
	
	static String parseToShortDate(date){
		DateFormat outformat= new SimpleDateFormat("dd-MM-yyyy");
		String d = outformat.format(date).toString()
		return d
	}
}