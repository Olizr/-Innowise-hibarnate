package DAO;

import models.Role;
import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class UserDaoIntegrationTest {
    private UserDao userDao;
    private List<User> users;
    private List<Role> roles;

    @Before
    public void initTest() {
        userDao = new UserDao();
        DatabaseInit.clearDatabase();
        users = EntityGenerator.createUsers(3);
        roles = EntityGenerator.createRoles(3);

        for (int i = 0; i < users.size() - 1; i++) {
            for (int j = 0; j < roles.size() - i -  1; j++) {
                users.get(i).getRoles().add(roles.get(j));
            }
        }

        DatabaseInit.initDatabase(roles, users);
    }

    @Test
    public void getExistUser() {
        User user = userDao.get(users.get(0).getId());
        Assert.assertEquals(users.get(0), user);
    }

    @Test
    public void getNotExistUser() {
        User user = userDao.get(-1);
        Assert.assertNull(user);
    }

    @Test
    public void getAll() {
        List<User> users = userDao.getAll();
        Assert.assertEquals(this.users.size(), users.size());
    }

    @Test
    public void getAllByRoleId() {
        List<User> users = userDao.getAllByRoleId(roles.get(0).getId());

        Assert.assertEquals(2, users.size());
    }

    @Test
    public void getAllByRoleIdWithNoUser() {
        List<User> users = userDao.getAllByRoleId(roles.get(2).getId());

        Assert.assertEquals(0, users.size());
    }

    @Test
    public void getAllByRoleName() {
        List<User> users = userDao.getAllByRoleName(roles.get(0).getName());

        Assert.assertEquals(2, users.size());
    }

    @Test
    public void getAllByRoleNameWithNoUser() {
        List<User> users = userDao.getAllByRoleId(roles.get(0).getId());

        Assert.assertEquals(2, users.size());
    }

    @Test
    public void save() {
        int before = userDao.getAll().size();

        User user = new User();
        user.setUsername("Username");
        user.setPassword("Password");
        user.setMail("Mail");
        userDao.save(user);

        List<User> users = userDao.getAll();
        Assert.assertNotEquals(before, users.size());
    }

    @Test
    public void update() {
        User user = this.users.get(0);
        String nameBefore = user.getUsername();
        user.setUsername(user.getUsername() + user.getUsername());
        userDao.update(user);

        Assert.assertNotEquals(nameBefore, userDao.get(user.getId()).getUsername());
    }

    @Test
    public void delete() {
        int before = userDao.getAll().size();

        userDao.save(users.get(0));

        List<User> users = userDao.getAll();
        Assert.assertNotEquals(before, users.size());
    }
}