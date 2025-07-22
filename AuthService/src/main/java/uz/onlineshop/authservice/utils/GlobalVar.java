package uz.onlineshop.authservice.utils;




import uz.onlineshop.authservice.config.UserPrincipal;
import uz.onlineshop.authservice.entity.User;

import java.time.LocalDateTime;

public class GlobalVar {

    private final static ThreadLocal<LocalDateTime> START_TIME = ThreadLocal.withInitial(LocalDateTime::now);
    private final static ThreadLocal<String> X_REQUESTED_ID = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<User> USER = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<UserPrincipal> AUTH_USER = ThreadLocal.withInitial(() -> null);

    public static User getUser() {
        return GlobalVar.USER.get();
    }

    public static LocalDateTime getStartTime() {
        return GlobalVar.START_TIME.get();
    }

    public static void initStartTime() {
        GlobalVar.START_TIME.set(LocalDateTime.now());
    }

    public static String getRequestId() {
        return GlobalVar.X_REQUESTED_ID.get();
    }

    public static void setRequestId(String requestId) {
        GlobalVar.X_REQUESTED_ID.set(requestId);
    }

    public static void setUser(User user) {
        GlobalVar.USER.set(user);
    }

    public static void setUserPrincipal(UserPrincipal userPrincipal) {
        GlobalVar.AUTH_USER.set(userPrincipal);
    }

    public static void clearContext() {
        GlobalVar.USER.remove();
        GlobalVar.AUTH_USER.remove();
    }


}
