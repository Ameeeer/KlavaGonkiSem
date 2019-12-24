package socketsapp.repositories;


import java.sql.SQLException;
import java.util.Optional;

public interface DBService extends CrudInterface {
    void saveMessageWithId(Long id, String message);
}
