package com.stack.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class Utils
{
	
	static final Logger logger= Logger.getLogger(com.stack.utility.Utils.class);
	
	/**
	 * This method converts the input string into a number and returns it
	 * 
	 * @param value
	 *            String value which is to be converted into number or integer
	 * @return int Integer converted string
	 */
	public static int parseInt(Object obj)
	{
		int numValue = 0;
		try
		{
			if (obj != null && obj.toString().trim().length() > 0)
			{
				numValue = Integer.parseInt(obj.toString().trim());
			}
		}
		catch (NumberFormatException nfe)
		{
			System.out.println("common.Utils.java ::Exception while converting a string value to integer");
			nfe.printStackTrace();
			return numValue;
		}
		return numValue;
	}
	
	public static Long parseLong(Object obj)
	{
		Long numValue = 0L;
		try
		{
			if (obj != null && obj.toString().trim().length() > 0)
			{
				numValue = Long.parseLong(getPlainString(obj.toString().trim()));
			}
		}
		catch (NumberFormatException nfe)
		{
			System.out.println("common.Utils.java ::Exception while converting a string value to Long");
			nfe.printStackTrace();
			return numValue;
		}
		return numValue;
	}

	public static double parseDouble(Object obj)
	 {
	  double numValue = 0.0;
	  try
	  {
	   if (obj != null && obj.toString().trim().length() > 0)
	   {
	    numValue = Double.parseDouble(obj.toString().trim());
	   }
	  }
	  catch (NumberFormatException nfe)
	  {
	   System.out.println("common.Utils.java ::Exception while converting a string value to integer");
	   nfe.printStackTrace();
	   return numValue;
	  }
	  return numValue;
	 }
	
	/**
	 * @param list
	 * @return
	 */
	public static StringBuffer getStringBufferForDB(ArrayList<String> list)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		ArrayList<String> cloneList = (ArrayList<String>) list.clone();
		if (list != null)
		{
			int i = 0;
			for (String tempStr : cloneList)
			{
				if (tempStr.contains("\""))
					tempStr = tempStr.replace("\"", "\\\"");
				if (tempStr.contains(","))
					tempStr = tempStr.replace(",", "\\,");
				if (i < cloneList.size() - 1)
					sb.append("\"" + tempStr + "\",");
				else
					sb.append("\"" + tempStr + "\"");
				i++;
			}
			sb.append("}");
		}
		return sb;
	}
	
	/**
	 * This method returns the value of the parameter to a zero length string
	 * from null value
	 * 
	 * @param parameter
	 *            String containing parameter
	 * @return String
	 */
	public static String checkNullValueForString(Object obj)
	{
		return ((obj == null) ? "" : obj.toString().trim());
	}
	
	/**
	 * This method returns the value of the parameter to a zero value from null
	 * value
	 * 
	 * @param parameter
	 *            String containing parameter
	 * @return String
	 */
	public static String checkNullValueForInt(Object obj)
	{
		String retVal = "0";
		if (obj == null)
		{
			retVal = "0";
		}
		else
		{
			retVal = obj.toString();
			if (retVal.length() == 0)
			{
				retVal = "0";
			}
		}
		return retVal;
	}
	
	public static String checkNullValueForDouble(Object obj)
	{
		
		String retVal = "0.00";
		if (obj == null)
		{
			retVal = "0.00";
		}
		else
		{
			retVal = obj.toString();
			if (retVal.length() == 0)
			{
				retVal = "0.00";
			}
		}
		return retVal;
	}
	
	public static String getPlainString(Object obj)
	{
		String retString = "";
		if(obj  != null)
		{
			retString = obj.toString().trim();
			retString = retString.replaceAll(" ","");
			retString = retString.replaceAll("_","");
			retString = retString.replaceAll(",","");
		}
		return retString;
	}
	
	public static long differenceInDays(Date startDate, Date endDate)
	{
		long diffInDays = 0L;
		long duration  = endDate.getTime() - startDate.getTime();
		
		diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
		
		return diffInDays;
	}
	
	@SuppressWarnings("deprecation")
	public static Date getDateFromString(String dateStr)
	{
		Date date = null;
		try 
		{
			DateFormat srcDf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			date = srcDf.parse(dateStr);
		}
		catch (Exception e) 
		{
			logger.info("exception  = " + dateStr);
			e.printStackTrace();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			try
			{
				date = df.parse("01/01/1970 00:00:00");
			}
			catch (ParseException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return date;
	}
	
	public static double round(double value, int places) 
	{
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static String extractWordsWithPrefix(String prefix, String text)
    {
        String username = "";
        String comma = "";
        ArrayList<String> listAllWords = new ArrayList<String>(Arrays.asList(text.split(" ")));
        ArrayList<String> listDesiredWords = new ArrayList<String>();
  
        for(String word : listAllWords)
        {
            if(word.startsWith(prefix))
            {
                if(word.indexOf(prefix, 1)>0)
                {
                    ArrayList<String> listAll = new ArrayList<String>(Arrays.asList(word.split(prefix)));
                    int count = 0;
                    for(String w :listAll)
                    {
                        if(count == 0)
                        { 
                            count++;
                            continue;
                        } 
                        listDesiredWords.add(prefix + w);
                        count++;
                    }
                }
                else
                { 
                    listDesiredWords.add(word);
                }
            }
        }
        for(String word: listDesiredWords)
        {
                username = username + comma + word;
                comma = " ";
        }	
        return username;
    } 
	
	public static String getCSVFromList(ArrayList<String> list)
    {
        String username = "";
        String comma = "";
        for(String word: list)
        {
                username = username + comma + word;
                comma = ", ";
        }	
	return username;
    }
	public static void copyFile(File sourceFile, File destFile) throws IOException 
	{
	    if (!sourceFile.exists()) 
	    {
	        return;
	    }
	    if (!destFile.exists()) 
	    {
	        destFile.createNewFile();
	    }
	    FileChannel source = null;
	    FileChannel destination = null;
	    source = new FileInputStream(sourceFile).getChannel();
	    destination = new FileOutputStream(destFile).getChannel();
	    if (destination != null && source != null) 
	    {
	        destination.transferFrom(source, 0, source.size());
	    }
	    if (source != null) 
	    {
	        source.close();
	    }
	    if (destination != null) 
	    {
	        destination.close();
	    }

	}
	public static String generateDocumentName(String documentName, int employeeId)
    {
		String[] documentNameArray = documentName.split("\\.");
		String documentNamePart1 = documentNameArray[0]; // 004
		String documentNamePart2 = documentNameArray[1];
		documentName = documentNamePart1+"-"+employeeId+"."+documentNamePart2;
		return documentName;
    }
	public static String deGenerateDocumentName(String documentName)
    {
		String[] arrayName = documentName.split("\\.");
		String arrayNamePart1 = arrayName[0];
		documentName = arrayNamePart1.split("-")[0] + "."+arrayName[1];
		return documentName;
    }
	public static String removeAllDotsExceptLast(String name)
	{
		int index = name.lastIndexOf(".");
		String part1 = name.substring(0,index);
		part1 = part1.replaceAll("\\.", "");
		String part2 = name.substring(index,name.length());
		return part1+part2; 

	}
}
