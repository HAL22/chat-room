package com.thethelafaltein.chatroom.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thethelafaltein.chatroom.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u where u.username = ?1 and u.password = ?2")
    Optional<User> findByUsernameAndPassword(String username,String password);
}
