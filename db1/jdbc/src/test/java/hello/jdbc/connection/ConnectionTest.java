package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
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

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        // spring 에서 jdbc를 사용하게 되면 HikariCP를 주입 받게 된다.
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
        Thread.sleep(3000);
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        // 만약 커넥션 풀에 커넥션이 만들어지지 않은 상태에서 Connection을 요청하면 풀에 생성이 될 때까지 기다렸다가 완료되면 가져올 수 있음
        // Pool 1개 있을 경우, 1개 요청은 처리 가능. 2개 요청 시 Pool 에 2개 생성될 떄까지 1개 요청은 대기
        Connection connection1 = dataSource.getConnection();
        Connection connection2 = dataSource.getConnection();

        log.info("connection = {}, class={}", connection1, connection1.getClass());
        log.info("connection = {}, class={}", connection2, connection2.getClass());
    }
}
