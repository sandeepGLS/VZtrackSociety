package vztrack.gls.com.vztracksociety.profile;

/**
 * Created by sandeep on 17/3/16.
 */
public class LoginResponse {
    String message;
    String code;
    Socity socity;
    User user;

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

    public Socity getSocity() {
        return socity;
    }

    public void setSocity(Socity socity) {
        this.socity = socity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
