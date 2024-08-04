package com.shop.shared.classes;

import com.shop.model.User;

public class UserThread {
    private static final ThreadLocal<User> user = new InheritableThreadLocal<>();

    public static void setUser(User u) {
        user.set(u);
    }

    public static User getUser() {
        return user.get();
    }

    public static Long getUserId() {
        return user.get().getUserId();
    }
}
