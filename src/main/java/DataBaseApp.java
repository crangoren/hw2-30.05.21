import java.sql.*;

public class DataBaseApp {

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psInsert;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connect();
        createDB();
        fillTableBatch();
        CloseDB();
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        System.out.println("БД подключена");
    }

    public static void createDB() throws SQLException {
        stmt = connection.createStatement();
        stmt.execute("DROP TABLE IF EXISTS auth;");
        stmt.execute("CREATE TABLE IF NOT EXISTS auth (" +
                "login      STRING          NOT NULL UNIQUE,\n" +
                "psw        STRING          NOT NULL,\n" +
                "nickname   STRING          NOT NULL UNIQUE);");
        System.out.println("Таблица 'auth' готова");
    }

    private static void fillTableBatch() throws SQLException {
        long begin = System.currentTimeMillis();
        psInsert = connection.prepareStatement("INSERT INTO auth (login, psw, nickname) VALUES (?, ?, ?);");
        connection.setAutoCommit(false);

        for (int i = 1; i <= 10; i++) {
            psInsert.setString(1, "log" + i);
            psInsert.setString(2, "psw" + i);
            psInsert.setString(3, "nick" + i);
            psInsert.executeUpdate();
        }
        psInsert.executeBatch();

        psInsert = connection.prepareStatement("INSERT INTO auth (login, psw, nickname) VALUES (?, ?, ?);");

        connection.setAutoCommit(true);

        long end = System.currentTimeMillis();
        System.out.printf("Time: %d ms ", end - begin);
        System.out.println("Таблица заполнена");
    }

    public static void CloseDB() throws ClassNotFoundException, SQLException {
        connection.close();
        stmt.close();
        System.out.println("Соединение закрыто");
    }
}



