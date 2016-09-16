package vztrack.gls.com.vztracksociety.beans;

/**
 * Created by sandeep on 6/4/16.
 */
public class AvailFlats {

    boolean active;
    int buildingId;
    int familyId;
    int flatFamilySize;
    String flatNo;
    String flatOwnerName;
    int flatVehickeSize;
    int societyId;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getFlatFamilySize() {
        return flatFamilySize;
    }

    public void setFlatFamilySize(int flatFamilySize) {
        this.flatFamilySize = flatFamilySize;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getFlatOwnerName() {
        return flatOwnerName;
    }

    public void setFlatOwnerName(String flatOwnerName) {
        this.flatOwnerName = flatOwnerName;
    }

    public int getFlatVehickeSize() {
        return flatVehickeSize;
    }

    public void setFlatVehickeSize(int flatVehickeSize) {
        this.flatVehickeSize = flatVehickeSize;
    }

    public int getSocietyId() {
        return societyId;
    }

    public void setSocietyId(int societyId) {
        this.societyId = societyId;
    }
}
