  package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

  /**
   * 예외 누수 해결
   * 체크 예외를 런타임 예뢰로 변경
   * MemberRepository interface 사용
   * throws SQLException 제거
   */
  @Slf4j
  public class MemberRepositoryV4_1 implements MemberRepository {
      private final DataSource dataSource;

      public MemberRepositoryV4_1(DataSource dataSource) {
          this.dataSource = dataSource;
      }

      @Override
      public Member save(Member member) {
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
              throw new MyDbException(e);
          } finally {
              close(pstmt, conn, null);
          }

      }

      @Override
      public Member findById(String memberId) {
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
              throw new MyDbException(e);
          } finally {
              close(pstmt, conn, rs);
          }
      }

      @Override
      public void update(String memberId, int money) {
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
              throw new MyDbException(e);
          } finally {
              close(pstmt, conn, null);
          }
      }

      @Override
      public void delete(String memberId) {
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
              throw new MyDbException(e);
          } finally {
              close(pstmt, conn, null);
          }
      }

      private void close(Statement pstmt, Connection conn, ResultSet rs) {
          JdbcUtils.closeResultSet(rs);
          JdbcUtils.closeStatement(pstmt);
          // 주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 함!
          DataSourceUtils.releaseConnection(conn, dataSource);
      }

      private Connection getConnection() {
          // 주의 ! 트랜잭션 동기화를 사용하려면 DataSourceUtils 를 사용해야 한다.
          // DataSourceUtils 는 spring 에서 지원해주는 기능
          Connection connection = DataSourceUtils.getConnection(dataSource);
          log.info("connection = {}, class={}", connection, connection.getClass());
          return connection;
      }
  }
