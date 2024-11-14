package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0  {
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        conn = getConnection();
        try {
            // prepareStatement를 사용해서 SQLInjection 공격 문제를 좀 더 예방할 수 있다.
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(pstmt, conn, null);
        }

    }

    private static void close(Statement pstmt, Connection conn, ResultSet rs) {
        // resource를 정리할 때는 항상 역순으로 진행해야 한다.
        // resource를 정리해주지 않으면 커넥션 연결을 끊지 않고 계속 유지하고 있을 수 있음.
        // 이 상황을 리소스 누수라고 하는데, 결과적으로 커넥션 부족 이슈로 이어질 수 있다.
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("error", e);
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.error("error", e);
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("error", e);
            }
        }
    }

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
