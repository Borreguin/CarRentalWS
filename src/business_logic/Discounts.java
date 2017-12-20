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
public class Discounts {

    private float totalDiscount;
    private Business_conf business_conf = new Business_conf();
    private App_config app_config = new App_config();

    /**
     * Allow a discount on weekdays, is enough that one day
     * in the list is a weekday to obtain the discount.
     * @param dates a list of dates in string format
     * @return x% discount if this applies, otherwise 0%
     */
    private float onWeekdays(List<String> dates) throws ParseException {
        float discount_percentage = 0.f;
        Calendar c = Calendar.getInstance();
        int dayOfWeek;
        for(String st_d: dates){
            Date day = app_config.formatter.parse(st_d);
            c.setTime(day);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if((dayOfWeek >= 1) && (dayOfWeek <= 5)){
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
    private float byNumberOfDays(List<String> dates){
        float discount_percentage = 0.f;

        return discount_percentage;
    }

    /**
     * Allow discount if client has a membership
     * @param isMember whether or not a client has a membership
     * @return x% discount if this applies, otherwise 0%
     */
    private float byMembership(Boolean isMember){
        float discount_percentage = 0.f;
        if(isMember) return Business_conf.getDiscountValues().get("membership");
        return discount_percentage;
    }
}
