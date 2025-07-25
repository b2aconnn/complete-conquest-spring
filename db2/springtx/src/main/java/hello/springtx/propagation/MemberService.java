package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    @Transactional
     public void joinV1(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== memberRepository call ==");
        memberRepository.save(member);
        log.info("== memberRepository exit ==");

        log.info("== logRepository call ==");
        logRepository. save(logMessage);
        log.info("== logRepository exit ==");
    }

    @Transactional
    public void joinV2(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username) ;

        log.info("== memberRepository call ==");
        memberRepository.save(member);
        log.info("== memberRepository exit ==");

        log.info("== logRepository call ==");
        try {
            logRepository.save(logMessage);
        } catch (RuntimeException e) {
            log.info("failed log save. logMessage={}", logMessage.getMessage());
            log.info("정상 흐름 반환");
        }
        log.info("== logRepository exit ==");
    }
}
