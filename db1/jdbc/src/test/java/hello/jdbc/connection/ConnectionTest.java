package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {
    @Test
    void driveManager() throws SQLException {
        Connection connection1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection connection2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        log.info("connection = {}, class={}", connection1, connection1.getClass());
        log.info("connection = {}, class={}", connection2, connection2.getClass());
    }

    @Test
    void datasourceDriverManager() throws SQLException {
        // DriveManagerDataSource : 항상 새로운 커넥션을 생성
        // spring 에서 제공해주는 DataSource (DriverManager를 사용할 때 추상화가 안 되어 있어서 추상화를 제공해주기 위해 사용)
        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection connection1 = dataSource.getConnection();
        Connection connection2 = dataSource.getConnection();

        log.info("connection = {}, class={}", connection1, connection1.getClass());
        log.info("connection = {}, class={}", connection2, connection2.getClass());
    }
}
