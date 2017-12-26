package business_logic;

/*
*   Author: Roberto Sanchez A. 
*   Date:   12/21/17
*/

import settings.Business_conf;

/**
 * Implement insurance policy rules
 */
class InsurancePolicy {

    /**
     * Generate an insurance policy and differentiate its price for people younger than 25 years old.
     * (5USD a day for the small car, 7USD a day for the sport car, 10USD a day for the SUV
     * with a 25% increase for younger people). No discount applies over the insurance total.
     *
     * @param nDays number of days
     * @param age   age of the client
     * @param typeCar   type of car
     * @return The total insurance policy
     */

    float apply(int nDays, int age, String typeCar){

        // @Business_conf contains the correspondent policy values.
        float insurancePerDay = Business_conf.getPolicyValues().get(typeCar);
        float subtotal = insurancePerDay*nDays;
        float recharge = 0;
        // recharge for young people:
        if(age < Business_conf.young_age){
            recharge = subtotal*Business_conf.recharge;
        }
        return subtotal + recharge;
    }
}
