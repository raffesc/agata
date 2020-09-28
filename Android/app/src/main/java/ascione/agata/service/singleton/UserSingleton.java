package ascione.agata.service.singleton;

import java.util.ArrayList;
import java.util.List;

import ascione.agata.service.network.response.UserListRecordResponse;
import ascione.agata.service.network.response.UserResponse;

public class UserSingleton {

    private UserResponse user;

    private List<UserListRecordResponse> allUsersList = new ArrayList<>();

    private static final UserSingleton ourInstance = new UserSingleton();

    public static UserSingleton getInstance() { return ourInstance; }

    private UserSingleton() {}


    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public List<UserListRecordResponse> getAllUsersList() {
        return allUsersList;
    }

    public void setAllUsersList(List<UserListRecordResponse> allUsersList) {
        this.allUsersList = allUsersList;
    }
}
