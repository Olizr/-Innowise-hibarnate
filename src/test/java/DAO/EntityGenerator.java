package DAO;

import models.Role;
import models.User;

import java.util.ArrayList;
import java.util.List;

public class EntityGenerator {
    public static List<User> createUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setUsername("Username" + i);
            user.setPassword("Password" + i);
            user.setMail("Mail" + i);
            users.add(user);
        }

        return users;
    }

    public static List<Role> createRoles(int count) {
        List<Role> roles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Role role = new Role();
            role.setName("Name" + i);
            role.setDescription("Description" + i);
            roles.add(role);
        }

        return roles;
    }
}
