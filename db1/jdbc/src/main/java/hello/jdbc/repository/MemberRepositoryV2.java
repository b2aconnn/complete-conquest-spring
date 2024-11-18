  package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

 /**
  * JDBC - Connection Param
  */
 @Slf4j
 public class MemberRepositoryV2 {
     private final DataSource dataSource;

     public MemberRepositoryV2(DataSource dataSource) {
         this.dataSource = dataSource;
     }

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
             throw e;
         } finally {
             close(pstmt, conn, rs);
         }
     }

     public Member findById(Connection conn,  String memberId) throws SQLException {
         String sql = "select * from member where member_id = ?";

         PreparedStatement  pstmt = null;
         ResultSet rs = null;
         try {
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
             throw e;
         } finally {
             JdbcUtils.closeResultSet(rs);
             JdbcUtils.closeStatement(pstmt);
             // transaction 을 사용해야 할 경우는 connection을 닫으면 안 됨!
             // 하나의 connection 으로 명령을 진행한 후에 commit, rollback 을 진행한 후에 닫아야 함
//             JdbcUtils.closeConnection(conn);
         }
     }

     public void update(String memberId, int money) throws SQLException {
         String sql = "update member set money = ? where member_id = ?";

         Connection conn = null;
         PreparedStatement pstmt = null;

         conn = getConnection();
         try {
             pstmt = conn.prepareStatement(sql);
             pstmt.setInt(1, money);
             pstmt.setString(2, memberId);
             int resultSize = pstmt.executeUpdate();
             log.info("resultSize = {}", resultSize);
         } catch (SQLException e) {
             log.error("db error", e);
             throw e;
         } finally {
             close(pstmt, conn, null);
         }
     }

     public void update(Connection conn, String memberId, int money) throws SQLException {
         String sql = "update member set money = ? where member_id = ?";

         PreparedStatement pstmt = null;
         try {
             pstmt = conn.prepareStatement(sql);
             pstmt.setInt(1, money);
             pstmt.setString(2, memberId);
             int resultSize = pstmt.executeUpdate();
             log.info("resultSize = {}", resultSize);
         } catch (SQLException e) {
             log.error("db error", e);
             throw e;
         } finally {
             JdbcUtils.closeStatement(pstmt);
             // t ransaction 을 사용해야 할 경우는 connection을 닫으면 안 됨!
//             JdbcUtils.closeConnection(conn);
         }
     }

     public void delete(String memberId) throws SQLException {
         String sql = "delete from member where member_id = ?";

         Connection conn = null;
         PreparedStatement pstmt = null;

         conn = getConnection();
         try {
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, memberId);
             int resultSize = pstmt.executeUpdate();
             log.info("resultSize = {}", resultSize);
         } catch (SQLException e) {
             log.error("db error", e);
             throw e;
         } finally {
             close(pstmt, conn, null);
         }
     }

     private static void close(Statement pstmt, Connection conn, ResultSet rs) {
         JdbcUtils.closeResultSet(rs);
         JdbcUtils.closeStatement(pstmt);
         JdbcUtils.closeConnection(conn);
     }

     private Connection getConnection() throws SQLException {
         Connection connection = dataSource.getConnection();
         log.info("connection = {}, class={}", connection, connection.getClass());
         return connection;
     }
 }
