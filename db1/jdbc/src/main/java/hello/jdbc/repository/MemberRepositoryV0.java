package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

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

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();
            // ResultSet에 처음에는 데이터를 바라볼 수 없다. 데이터가 있는 지 확인하기 위해서 next()를 호출해줘야 함.
            // cursor 기반으로 되어 있기 때문에 next() 를 통해 다음 데이터를 가져옴.
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            close(pstmt, conn, rs);
        }

        return new Member();
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
