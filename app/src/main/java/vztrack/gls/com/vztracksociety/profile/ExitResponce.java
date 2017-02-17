package vztrack.gls.com.vztracksociety.profile;


import java.util.ArrayList;

import vztrack.gls.com.vztracksociety.beans.AvailFlats;
import vztrack.gls.com.vztracksociety.beans.VisitorBean;


/**
 * Created by sandeep on 19/9/16.
 */

public class ExitResponce {

    String message;
    String code;
    ArrayList<VisitorBean> visitors=new ArrayList<>();


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

    public ArrayList<VisitorBean> getVisitors() {
        return visitors;
    }

    public void setVisitors(ArrayList<VisitorBean> visitors) {
        this.visitors = visitors;
    }
}

