package com.thethelafaltein.chatroom.db;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thethelafaltein.chatroom.model.Chat;
import com.thethelafaltein.chatroom.model.ChatStatus;

import jakarta.transaction.Transactional;


@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c where c.customerId = ?1")
    Optional<List<Chat>> findByCustomerId(Long customerId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional  
    @Query("UPDATE Chat c set c.chatStatus = ?2 where c.id = ?1 and c.customerId = ?3")
    void updateChatStatus(Long id,ChatStatus status,Long customerId);

    @Query("SELECT c FROM Chat c where c.chatStatus = ?1")
    Optional<List<Chat>> findAllByStatus(ChatStatus status);
}
