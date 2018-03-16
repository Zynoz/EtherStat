package exceptions;

import java.sql.SQLException;

public class MySQLException extends SQLException {
    public MySQLException(String reason) {
        super(reason);
    }
}