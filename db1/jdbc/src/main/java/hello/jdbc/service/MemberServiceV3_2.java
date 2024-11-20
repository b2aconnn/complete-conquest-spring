  package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.SQLException;

  /**
  * 트랜잭션 - 트랜잭션 템플릿
  */

  @Slf4j
  //@Service
  public class MemberServiceV3_2 {
//      private final PlatformTransactionManager transactionManager;
      private final TransactionTemplate txTemplate;
      private final MemberRepositoryV3 memberRepository;

      public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
          this.txTemplate = new TransactionTemplate(transactionManager);
          this.memberRepository = memberRepository;
      }

      public void accountTransfer(String fromId, String toId, int money) throws SQLException {

          txTemplate.executeWithoutResult(status -> {
              try {
                  bizLogic(fromId, toId, money);
              } catch (SQLException e) {
                  throw new IllegalStateException();
              }
          });
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
