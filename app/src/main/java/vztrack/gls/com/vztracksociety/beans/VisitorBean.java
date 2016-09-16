package vztrack.gls.com.vztracksociety.beans;

/**
 * Created by sandeep on 22/3/16.
 */
public class VisitorBean {
    int visitor_id;
    int socity_id;
    String socity_name;
    int building_id;
    String mobile_no;
    String first_name;
    String last_name;
    String flat_no;
    String visit_purpose;
    String vehicle_no;
    boolean is_verified;
    String in_time;
    String out_time;
    int watchman_id;
    String watchmen_name;
    String watchmen_mobile;
    String photo_url;
    String gate_no;
    String doc_url;
    int no_of_visitors;
    String whomeToVisit;
    String from;
    boolean oldDoc;
    boolean old;

    public boolean isOld() {
        return old;
    }

    public void setOld(boolean old) {
        this.old = old;
    }

    public boolean isOldDoc() {
        return oldDoc;
    }

    public void setOldDoc(boolean oldDoc) {
        this.oldDoc = oldDoc;
    }

    public boolean is_verified() {
        return is_verified;
    }

    public int getVisitor_id() {
        return visitor_id;
    }
    public void setVisitor_id(int visitor_id) {
        this.visitor_id = visitor_id;
    }
    public int getSocity_id() {
        return socity_id;
    }
    public void setSocity_id(int socity_id) {
        this.socity_id = socity_id;
    }
    public String getSocity_name() {
        return socity_name;
    }
    public void setSocity_name(String socity_name) {
        this.socity_name = socity_name;
    }
    public int getBuilding_id() {
        return building_id;
    }
    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
    }
    public String getMobile_no() {
        return mobile_no;
    }
    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getFlat_no() {
        return flat_no;
    }
    public void setFlat_no(String flat_no) {
        this.flat_no = flat_no;
    }
    public String getVisit_purpose() {
        return visit_purpose;
    }
    public void setVisit_purpose(String visit_purpose) {
        this.visit_purpose = visit_purpose;
    }
    public String getVehicle_no() {
        return vehicle_no;
    }
    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }
    public boolean isIs_verified() {
        return is_verified;
    }
    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }
    public String getIn_time() {
        return in_time;
    }
    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }
    public String getOut_time() {
        return out_time;
    }
    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }
    public int getWatchman_id() {
        return watchman_id;
    }
    public void setWatchman_id(int watchman_id) {
        this.watchman_id = watchman_id;
    }
    public String getWatchmen_name() {
        return watchmen_name;
    }
    public void setWatchmen_name(String watchmen_name) {
        this.watchmen_name = watchmen_name;
    }
    public String getWatchmen_mobile() {
        return watchmen_mobile;
    }
    public void setWatchmen_mobile(String watchmen_mobile) {
        this.watchmen_mobile = watchmen_mobile;
    }
    public String getPhoto_url() {
        return photo_url;
    }
    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
    public String getGate_no() {
        return gate_no;
    }
    public void setGate_no(String gate_no) {
        this.gate_no = gate_no;
    }
    public String getDoc_url() {
        return doc_url;
    }
    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }
    public int getNo_of_visitors() {
        return no_of_visitors;
    }
    public void setNo_of_visitors(int no_of_visitors) {
        this.no_of_visitors = no_of_visitors;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getWhomeToVisit() {
        return whomeToVisit;
    }

    public void setWhomeToVisit(String whomeToVisit) {
        this.whomeToVisit = whomeToVisit;
    }
}
