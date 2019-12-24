package socketsapp.repositories;


import socketsapp.connetions.MyConnection;
import socketsapp.models.User;
import socketsapp.rowmappers.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

public class CrudImpl implements DBService {
    private MyConnection myConnection = new MyConnection();
    private Connection connection = myConnection.connectToDB();

    public CrudImpl() {
    }


    @Override
    public boolean saveUser(User user) throws SQLException {
        Optional<User> user1 = readUser(user);
        String sqlQuery = "INSERT INTO appusers(login, password) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            System.out.println("On save user");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("this user exist");
        }
    }
    public RowMapper<User> userRowMapper = rs ->
            User.newBuilder().setId(rs.getLong("id")).setLogin(rs.getString("login")).setPassword(rs.getString("password")).build();
    //                    rs.getLong("id"),
//                    rs.getString("login"),
//                    rs.getString("password")
    public RowMapper<User> userIdMapper = rs ->
            User.newBuilder().setId(rs.getLong("id")).setRole(rs.getString("role")).build();
//                    rs.getLong("id"),
//                    rs.getString("role")

    public RowMapper<User> userRowedWithToken = rs ->
            User.newBuilder().setLogin(rs.getString("login")).setToken(rs.getString("token")).build();
//                    rs.getString("login"),
//                    rs.getString("token")

    @Override
    public Optional<User> readUser(User user) throws SQLException {
        String sqlQuery = "SELECT * from appusers where login = ? and password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                User u = null;
                if (resultSet.next()) {
                    u = userRowMapper.mapRow(resultSet);
                    return Optional.of(u);
                } else {
                    return Optional.ofNullable(u);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("UserExist or db error");
        }
    }

    @Override
    public boolean updateUser() {
        return false;
    }

    @Override
    public boolean deleteUser() {
        return false;
    }

    @Override
    public void saveMessageWithId(Long id, String message) {
        String sqlQuery = "INSERT into idsandmessages (id, time, message) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            Date date = new Date();
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, String.valueOf(date));
            preparedStatement.setString(3, message);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
