package hello.springtx.propagation;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LogRepository {
    private final EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Log logMessage) {
        log.info("log save");
        em.persist(logMessage);

        if (logMessage.getMessage().contains("로그예외")) {
            log.info("log save exception");
            throw new RuntimeException("exception occurred");
        }
    }

    public Optional<Log> find(String message) {
        return em.createQuery("select l from Log l where l.message = :message", Log.class)
                .setParameter("message", message)
                .getResultList()
                .stream()
                .findAny();
    }
}
