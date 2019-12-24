package socketsapp.services;


import java.sql.SQLException;

import org.apache.commons.codec.binary.Base64;
import socketsapp.models.User;
import socketsapp.repositories.CrudImpl;
import socketsapp.repositories.DBService;

import java.util.Optional;

public class AuthService implements AuthServiceInteface {
    private DBService dbService = new CrudImpl();// TODO: 23.11.2019 Add ApplicationContext (component)

    public AuthService() {
    }

    @Override
    public boolean isAdmin(User user) {

        return false;
    }


    @Override
    public User authUser(User user) {
        try {
            Optional<User> checkUser = dbService.readUser(user);
            if (checkUser.isPresent()) {
                return checkUser.get();
            } else {
                registrateUser(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String registrateUser(User user) {

        return null;
    }
}
