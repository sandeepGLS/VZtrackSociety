package vztrack.gls.com.vztracksociety.profile;

import java.util.ArrayList;

import vztrack.gls.com.vztracksociety.beans.AvailFlats;

/**
 * Created by sandeep on 6/4/16.
 */
public class FlatResponce {

    ArrayList<AvailFlats> availFlats=new ArrayList<>();

    public ArrayList<AvailFlats> getAvailFlats() {
        return availFlats;
    }

    public void setAvailFlats(ArrayList<AvailFlats> availFlats) {
        this.availFlats = availFlats;
    }
}
