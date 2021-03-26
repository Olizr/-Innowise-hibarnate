package DAO;

import models.Role;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for accessing data using JDBC
 */
public class RoleDao extends Dao<Role> {
    private final String SQL_GET_ALL = "FROM Role";
    private final String SQL_GET_BY_USER_ID = "SELECT role FROM Role role JOIN role.users user WHERE user.id = :userId";
    private final String SQL_GET_BY_USER_NAME = "SELECT role FROM Role role JOIN role.users user WHERE user.username = :username";

    public RoleDao() {
        super();
    }

    /**
     * Searching for role with id
     *
     * @param id Role id to find
     * @return Role with giving id
     */
    public Role get(int id) {
        return getById(id, Role.class);
    }

    /**
     * Takes all roles from table
     *
     * @return List of all roles in table
     */
    public List<Role> getAll() {
        return getAll(SQL_GET_ALL);
    }

    /**
     * Takes all role from table that's have role with
     * specific id
     *
     * @param id Role id to search
     * @return List of roles
     */
    public List<Role> getAllByUserId(int id) {
        List<Role> roles = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(SQL_GET_BY_USER_ID);
        query.setParameter("userId", id);

        for (Object o : query.getResultList()) {
            roles.add((Role) o);
        }

        session.close();

        return roles;
    }

    /**
     * Takes all role from table that's have role with
     * specific name
     *
     * @param userName Name of role to search
     * @return List of roles
     */
    public List<Role> getAllByUserName(String userName) {
        List<Role> roles = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(SQL_GET_BY_USER_NAME);
        query.setParameter("username", userName);

        for (Object o : query.getResultList()) {
            roles.add((Role) o);
        }

        session.close();

        return roles;
    }
}
