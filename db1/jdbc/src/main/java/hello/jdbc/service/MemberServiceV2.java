  package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
* 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
*/

@Slf4j
  @RequiredArgsConstructor
//@Service
public class MemberServiceV2 {
    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false); // 트랜잭션 시작
            // 비즈니스 로직
             bizLogic(conn, fromId, toId, money);
            conn.commit(); // 성공 시 커밋
        } catch (Exception e) {
            conn.rollback();
            throw new IllegalStateException();
        } finally {
            release(conn);
        }
    }

      private void bizLogic(Connection conn, String fromId, String toId, int money) throws SQLException {
          Member fromMember = memberRepository.findById(conn, fromId);
          Member toMember = memberRepository.findById(conn, toId);

          memberRepository.update(conn, fromId, fromMember.getMoney() - money);
          validation(toMember);
          memberRepository.update(conn, toId, toMember.getMoney() + money);
      }

      private static void release(Connection conn) {
          if (conn != null) {
              try {
                  // auto commit 을 true 로 변경하지 않으면 connection pool 로 반환할 때 false인 connection으로 반환 되어서
                  // connection pool 을 사용할 때는 true 로 변경해줘야 함
                  // 구현체 마다 close() 내부에서 true 로 설정해주기도 함. (ex. h2 DB는 내부에서 닫아줌)
                  conn.setAutoCommit(true);
                  conn.close();
              } catch (Exception e) {
                  log.error("error", e);
              }
          }
      }

      private static void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
