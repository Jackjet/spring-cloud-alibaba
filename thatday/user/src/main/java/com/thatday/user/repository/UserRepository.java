package com.thatday.user.repository;

import com.thatday.user.entity.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    List<User> findUserByPhoneAndPassword(String phone, String psw);

    List<User> findUserByWeChatOpenId(String openId);

    User findByIdEquals(Long userId);

}