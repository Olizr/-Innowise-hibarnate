package DAO;

import models.Role;
import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class RoleDaoIntegrationTest {
    private RoleDao roleDao;
    private List<User> users;
    private List<Role> roles;

    @Before
    public void initTest() {
        roleDao = new RoleDao();
        DatabaseInit.clearDatabase();
        users = EntityGenerator.createUsers(3);
        roles = EntityGenerator.createRoles(3);

        for (int i = 0; i < roles.size() - 1; i++) {
            for (int j = 0; j < roles.size() - i -  1; j++) {
                users.get(i).getRoles().add(roles.get(j));
            }
        }

        DatabaseInit.initDatabase(roles, users);
    }

    @Test
    public void getExistRole() {
        Role role = roleDao.get(roles.get(0).getId());
        Assert.assertEquals(roles.get(0), role);
    }

    @Test
    public void getNotExistRole() {
        Role role = roleDao.get(-1);
        Assert.assertNull(role);
    }

    @Test
    public void getAll() {
        List<Role> roles = roleDao.getAll();
        Assert.assertEquals(this.roles.size(), roles.size());
    }

    @Test
    public void getAllByUserId() {
        List<Role> roles = roleDao.getAllByUserId(users.get(0).getId());

        Assert.assertEquals(2, roles.size());
    }

    @Test
    public void getAllByUserIdWithNoRole() {
        List<Role> roles = roleDao.getAllByUserId(users.get(2).getId());

        Assert.assertEquals(0, roles.size());
    }

    @Test
    public void getAllByUserName() {
        List<Role> roles = roleDao.getAllByUserName(users.get(0).getUsername());

        Assert.assertEquals(2, roles.size());
    }

    @Test
    public void getAllByUserNameWithNoRole() {
        List<Role> roles = roleDao.getAllByUserName(users.get(2).getUsername());

        Assert.assertEquals(0, roles.size());
    }

    @Test
    public void save() {
        int before = roleDao.getAll().size();

        Role role = new Role();
        role.setName("Name");
        role.setDescription("Desc");
        roleDao.save(role);

        List<Role> roles = roleDao.getAll();
        Assert.assertNotEquals(before, roles.size());
    }

    @Test
    public void update() {
        Role role = this.roles.get(0);
        String nameBefore = role.getName();
        role.setName(role.getName() + role.getName());
        roleDao.update(role);

        Assert.assertNotEquals(nameBefore, roleDao.get(role.getId()).getName());
    }

    @Test
    public void delete() {
        int before = roleDao.getAll().size();

        roleDao.save(roles.get(0));

        List<Role> roles = roleDao.getAll();
        Assert.assertNotEquals(before, roles.size());
    }
}
