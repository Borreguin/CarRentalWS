package classes;

import org.json.JSONObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/*
*   Author: Roberto Sanchez A. 
*   Date:   12/25/17
*/

/**
 * @Transaction.class implements a class to keep the inputJSON string and the outputJSON string
 * calculated according to the @business_logic
 *
 * inputJSON: is the input Json in format:
 *      {"rentDates":["2017-11-19T05:00:00.000Z","2017-11-20T05:00:00.000Z",...],
 *      "car":{"model":"Cherato","type":"sport"},"membership":false,"age":24}
 *
 *
 * outputJSON: is the output Json in format:
 *      {"subtotal":350,"insuranceTotal":53,"discountPercentage":22.5,
 *      "totalPayment":324.25, "transaction":"successful"}
 *
 *  note: the key "transaction" was added in order to say whether a transaction was successful or not.
 */

@XmlRootElement(name = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    private String inputJsonSt;
    private String outputJsonSt;
    private int id;

    public Transaction(int id, JSONObject inputJSON, JSONObject outputJSON) {
        this.id = id;
        this.inputJsonSt = inputJSON.toString();
        this.outputJsonSt = outputJSON.toString();
    }

    public int getId() {
        return id;
    }
    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getInputJsonSt() {
        return inputJsonSt;
    }

    public void setInputJsonSt(String inputJsonSt) {
        this.inputJsonSt = inputJsonSt;
    }

    public String getOutputJsonSt() {
        return outputJsonSt;
    }

    public void setOutputJsonSt(String outputJsonSt) {
        this.outputJsonSt = outputJsonSt;
    }
}
