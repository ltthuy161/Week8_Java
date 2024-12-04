package Service;

import DAO.UserDAO;
import Model.User;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void addUser(User user) throws SQLException {
        if (user.getName() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("Name and email cannot be null.");
        }
        userDAO.addUser(user);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }
}
