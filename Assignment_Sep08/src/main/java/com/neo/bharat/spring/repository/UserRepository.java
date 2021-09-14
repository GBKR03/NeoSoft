package com.neo.bharat.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neo.bharat.spring.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findAllByFirstName(String firstName);
    public List<User> findAllBySurName(String surName);
    public List<User> findAllByPincode(String pincode);
}
