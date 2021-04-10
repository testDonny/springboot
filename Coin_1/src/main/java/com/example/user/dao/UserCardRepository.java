package com.example.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.user.model.UserCard;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, String>{

}
