package vztrack.gls.com.vztracksociety.profile;

public class User {

	String user_active;
	String user_first_name;
	String user_id;
	String user_last_name;
	String user_name;
	String user_password;
	String user_role_id;
	int ui_exit_active;
	int type;
	int purposeCount;
	int flatCount;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUi_exit_active() {
		return ui_exit_active;
	}

	public void setUi_exit_active(int ui_exit_active) {
		this.ui_exit_active = ui_exit_active;
	}

	public String getUser_active() {
		return user_active;
	}

	public void setUser_active(String user_active) {
		this.user_active = user_active;
	}

	public String getUser_first_name() {
		return user_first_name;
	}

	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_last_name() {
		return user_last_name;
	}

	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_role_id() {
		return user_role_id;
	}

	public void setUser_role_id(String user_role_id) {
		this.user_role_id = user_role_id;
	}

	public int getPurposeCount() {
		return purposeCount;
	}

	public void setPurposeCount(int purposeCount) {
		this.purposeCount = purposeCount;
	}

	public int getFlatCount() {
		return flatCount;
	}

	public void setFlatCount(int flatCount) {
		this.flatCount = flatCount;
	}
}
