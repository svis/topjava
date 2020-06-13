package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(0, "user 0", "email 0@yandex.ru", "password 0", Role.ADMIN),
            new User(1, "user 1", "email 1@gmail.com", "password 1", Role.USER),
            new User(2, "user 2", "email 2@mail.ru", "password 2", Role.USER),
            new User(3, "user 3", "email 3@yahoo.com", "password 3", Role.USER),
            new User(4, "user 4", "email 4@javaops.ru", "password 4", Role.ADMIN)
    );

    public static List<User> getUsers() {
        return USERS;
    }
}