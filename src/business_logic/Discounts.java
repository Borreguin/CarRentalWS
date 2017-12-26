package business_logic;

import settings.App_config;
import settings.Business_conf;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 *   Author: Roberto Sanchez A. 
 *   Date:   12/20/17
 */

/**
 * Implements all kind of discounts
 */
class Discounts {

    private App_config app_config = new App_config();

    /**
     * Allow a discount on weekdays, is enough that one day
     * in the list is a weekday to obtain the discount.
     * @param dates a list of dates in string format
     * @return x% discount if this applies, otherwise 0%
     */
    float onWeekdays(List<String> dates) throws ParseException {
        float discount_percentage = 0.f;
        // A Gregorian calender is used for simplicity
        Calendar c = Calendar.getInstance();
        int dayOfWeek;
        // Check whether a date is a week day
        for(String st_d: dates){
            // Setup the formatter in @App_config.class
            Date day = app_config.formatter.parse(st_d);
            c.setTime(day);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            // Discounts values in @Business_conf.class
            if(dayOfWeek >= App_config.idMonday && dayOfWeek <= App_config.idMonday + 4 ){
                return Business_conf.getDiscountValues().get("onWeekday");
            }
        }
        return discount_percentage;
    }

    /**
     * Allow a specific discount depending on the number of rental days
     * @param dates a list of dates in string format
     * @return x% discount if this applies, otherwise 0%
     */
    float byNumberOfDays(List<String> dates){
        float discount_percentage = 0.f;
        int nDays = dates.size();

        // Discounts values in @Business_conf.class
        if( 3 <= nDays && nDays <= 5){
            return Business_conf.getDiscountValues().get("3 to 5");
        }
        if( 6 <= nDays && nDays <= 10){
            return Business_conf.getDiscountValues().get("6 to 10");
        }
        if( 11 <= nDays ){
            return Business_conf.getDiscountValues().get("> 11");
        }
        return discount_percentage;
    }

    /**
     * Allow discount if client has a membership
     * @param isMember whether or not a client has a membership
     * @return x% discount if this applies, otherwise 0%
     */
    float byMembership(Boolean isMember){
        float discount_percentage = 0.f;
        // Discounts values in @Business_conf.class
        if(isMember)
            return Business_conf.getDiscountValues().get("membership");
        return discount_percentage;
    }


}
