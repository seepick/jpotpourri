package net.sourceforge.jpotpourri.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtDateUtil {

	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";
	
	private static final DateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat(TIMESTAMP_FORMAT);
	
    private PtDateUtil() {
        // no instantiation
    }
    
    public static Calendar getCalendarWithoutTime(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
    }
    
    public static Calendar getCalendarWithoutMilliSeconds(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
    }
    
    public static Date getDateWithoutTime(final Date date) {
        return getCalendarWithoutTime(date).getTime();
    }
    
    public static Date getDateWithoutMilliSeconds(final Date date) {
        return getCalendarWithoutMilliSeconds(date).getTime();
    }
    
    public static Date getDateWithoutTimeAndChangedDays(final Date date, final int changeDays) {
        Calendar c = getCalendarWithoutTime(date);
        c.add(Calendar.DAY_OF_MONTH, changeDays);
        return c.getTime();
    }
    
    /**
     * @param daysBefore non negative number
     */
    public static Date getCurrentDateWithoutTimeAndSubtractedDays(final int daysBefore) {
        assert(daysBefore >= 0);
        
        return getDateWithoutTimeAndChangedDays(new Date(), -daysBefore);
    }
    
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }
    
    public static int compareWithoutTime(final Date d1, final Date d2) {
        return getDateWithoutTime(d1).compareTo(getDateWithoutTime(d2));
    }
    
    public static int compareWithoutMilliSeconds(final Date d1, final Date d2) {
        return getDateWithoutMilliSeconds(d1).compareTo(getDateWithoutMilliSeconds(d2));
    }

    private static boolean validateTimestampNumber(
    		final String string,
    		final int from,
    		final int to,
    		final int minInclusive,
    		final int maxInclusive) {
    	
    	final int len = string.length();
    	if(len > from) {
    		final String part = string.substring(from, Math.min(to, len));
//    		System.out.println("part=[" + part + "]");
    		if(isIntNumber(part) == false) {
    			return false;
    		}
    		final int number = Integer.parseInt(part);
    		return number <= maxInclusive && number >= minInclusive;
    	}
    	return true; // string is not long enough to check anything
    }

    private static boolean validateTimestampChar(
    		final String string,
    		final int pos,
    		final char c
    	) {
    	if(string.length() > pos && string.charAt(pos) != c) {
//			System.err.println("charAt(" + pos + ") [" + string.charAt(pos) + "] != [" + c + "]");
			return false;
    	}
    	return true;
    }

    public static String formatTimestamp(final long timestamp) {
    	return TIMESTAMP_FORMATTER.format(new Date(timestamp));
    }

    public static long parseTimestamp(final String source) throws ParseException {
    	return TIMESTAMP_FORMATTER.parse(source).getTime();
    }
    
    /**
     * @param string something like "yyyy-MM-dd HH:mm:ss.SSSSSS"
     */
    public static boolean validateTimestampPart(final String string) {
//    	System.out.println("validating part [" + string + "]");
    	final int len = string.length();
    	
//    	System.out.println("len: " + len);
    	if(len > TIMESTAMP_FORMAT.length()) {
//    		System.err.println("overlength!");
    		return false;
    	}

    	// msec
    	if(validateTimestampNumber(string, 20, 26, 0, 999999) == false) {
    		return false;
    	}

    	if(validateTimestampChar(string, 19, '.') == false) {
    		return false;
    	}

    	// sec
    	if(validateTimestampNumber(string, 17, 19, 0, 59) == false) {
    		return false;
    	}

    	if(validateTimestampChar(string, 16, ':') == false) {
    		return false;
    	}
    	
    	// minute
    	if(validateTimestampNumber(string, 14, 16, 0, 59) == false) {
    		return false;
    	}

    	if(validateTimestampChar(string, 13, ':') == false) {
    		return false;
    	}
    	
    	// hour
    	if(validateTimestampNumber(string, 11, 13, 0, 23) == false) {
    		return false;
    	}

    	if(validateTimestampChar(string, 10, ' ') == false) {
    		return false;
    	}

    	// day
    	if(validateTimestampNumber(string, 8, 10, 1, 31) == false) {
    		return false;
    	}

    	if(validateTimestampChar(string, 7, '-') == false) {
    		return false;
    	}

    	// month
    	if(validateTimestampNumber(string, 5, 7, 1, 12) == false) {
    		return false;
    	}

    	if(validateTimestampChar(string, 4, '-') == false) {
    		return false;
    	}
    	
    	// year
    	if(validateTimestampNumber(string, 0, 4, 0, 9999) == false) {
    		return false;
    	}

    	return true;
    }
    
    private static boolean isIntNumber(final String string) {
    	for (int i = 0; i < string.length(); i++) {
			final char c = string.charAt(i);
			if(Character.isDigit(c) == false) {
				return false;
			}
		}
    	return true;
    }
    
    
    
    public static void main(final String[] args) throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        List<Date> dates = new LinkedList<Date>();
        dates.add(format.parse("2008-01-01 12:00:01.126"));
        dates.add(format.parse("2008-01-01 12:00:01.124"));
        dates.add(format.parse(""));
        Collections.sort(dates, new Comparator<Date>() {
            public int compare(final Date d1, final Date d2) {
                return compareWithoutMilliSeconds(d1, d2);
            }
            
        });
        System.out.println(Arrays.toString(dates.toArray()));
    }
}
