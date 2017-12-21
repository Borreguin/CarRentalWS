package business_logic;

/*
*   Author: Roberto Sanchez A. 
*   Date:   12/21/17
*/

import settings.Business_conf;

/**
 * Implement insurance policy rules
 */
public class InsurancePolicy {

    public float apply(int nDays, int age, String typeCar){
        float insurancePerDay = Business_conf.getPolicyValues().get(typeCar);
        float subtotal = insurancePerDay*nDays;
        float recharge = 0;
        if(age < Business_conf.young_age){
            recharge = subtotal*Business_conf.recharge;
        }
        return subtotal + recharge;
    }
}
