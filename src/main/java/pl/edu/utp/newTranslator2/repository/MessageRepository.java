package pl.edu.utp.newTranslator2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.utp.newTranslator2.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
}
