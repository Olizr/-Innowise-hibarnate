package DAO;

import models.User;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for accessing data using JDBC
 */
public class UserDao extends Dao<User> {
    private final String SQL_GET_ALL = "FROM User";
    private final String SQL_GET_BY_ROLE_ID = "SELECT user FROM User user JOIN user.roles role WHERE role.id = :roleId";
    private final String SQL_GET_BY_ROLE_NAME = "SELECT user FROM User user JOIN user.roles role WHERE role.name = :roleName";

    public UserDao() {
        super();
    }

    /**
     * Searching for user with id
     *
     * @param id User id to find
     * @return User with giving id
     */
    public User get(int id) {
        return getById(id, User.class);
    }

    /**
     * Takes all user from table
     *
     * @return List of all users in table
     */
    public List<User> getAll() {
        return getAll(SQL_GET_ALL);
    }

    /**
     * Takes all user from table that's have role with
     * specific id
     *
     * @param id Role id to search
     * @return List of users
     */
    public List<User> getAllByRoleId(int id) {
        List<User> users = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(SQL_GET_BY_ROLE_ID);
        query.setParameter("roleId", id);

        for (Object o : query.getResultList()) {
            users.add((User) o);
        }

        session.close();

        return users;
    }

    /**
     * Takes all user from table that's have role with
     * specific name
     *
     * @param roleName Name of role to search
     * @return List of users
     */
    public List<User> getAllByRoleName(String roleName) {
        List<User> users = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(SQL_GET_BY_ROLE_NAME);
        query.setParameter("roleName", roleName);

        for (Object o : query.getResultList()) {
            users.add((User) o);
        }

        session.close();

        return users;
    }
}
