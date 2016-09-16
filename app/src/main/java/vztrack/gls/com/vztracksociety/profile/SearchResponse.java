package vztrack.gls.com.vztracksociety.profile;

import java.util.ArrayList;

import vztrack.gls.com.vztracksociety.beans.VisitorBean;

/**
 * Created by sandeep on 17/3/16.
 */
public class SearchResponse {
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
