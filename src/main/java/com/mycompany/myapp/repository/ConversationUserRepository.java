package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ConversationUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationUserRepository extends JpaRepository<ConversationUser, Long> {
    Optional<ConversationUser> findById(Long id);
    ConversationUser findByConversationId(Long conversationId);
}
