package profiles.tmp;

public class LastLogin {
    private static Integer lastUserId;

    public void setLastLogin(Integer id){
        lastUserId = id;
    }

    public Integer getLastUserId(Integer id){
        return lastUserId;
    }
}