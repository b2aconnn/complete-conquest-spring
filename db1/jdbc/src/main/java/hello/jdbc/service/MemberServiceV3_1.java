  package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

  /**
  * 트랜잭션 - 트랜잭션 매니저
  */

  @Slf4j
    @RequiredArgsConstructor
  //@Service
  public class MemberServiceV3_1 {
//      private final DataSource dataSource;

      private final PlatformTransactionManager transactionManager;
      private final MemberRepositoryV3 memberRepository;

      public void accountTransfer(String fromId, String toId, int money) throws SQLException {
          TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
          try {
              // 비즈니스 로직
               bizLogic(fromId, toId, money);
               transactionManager.commit(status);
          } catch (Exception e) {
              transactionManager.rollback(status);
              throw new IllegalStateException();
          }
      }

        private void bizLogic(String fromId, String toId, int money) throws SQLException {
            Member fromMember = memberRepository.findById(fromId);
            Member toMember = memberRepository.findById(toId);

            memberRepository.update(fromId, fromMember.getMoney() - money);
            validation(toMember);
            memberRepository.update(toId, toMember.getMoney() + money);
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
