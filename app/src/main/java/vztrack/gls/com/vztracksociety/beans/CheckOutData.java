package vztrack.gls.com.vztracksociety.beans;

import java.util.ArrayList;

/**
 * Created by sandeep on 16/9/16.
 */
public class CheckOutData {

    String message;
    String code;
    ArrayList<CheckOutBean> checkOutBeen;

    public ArrayList<CheckOutBean> getCheckOutBeen() {
        return checkOutBeen;
    }

    public void setCheckOutBeen(ArrayList<CheckOutBean> checkOutBeen) {
        this.checkOutBeen = checkOutBeen;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}