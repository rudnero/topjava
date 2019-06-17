package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "Коля", "Nik@mail.com", "pwd3", Role.ROLE_USER),
            new User(null, "Ваня", "Ivan@mail.com", "pwd1", Role.ROLE_USER),
            new User(null, "Петя", "Piter@mail.com", "pwd2", Role.ROLE_USER),
            new User(null, "Коля", "Nik@mail.com", "pwd4", Role.ROLE_USER),
            new User(null, "Алиса", "Alis@mail.com", "pwd5", Role.ROLE_USER)
    );
}
