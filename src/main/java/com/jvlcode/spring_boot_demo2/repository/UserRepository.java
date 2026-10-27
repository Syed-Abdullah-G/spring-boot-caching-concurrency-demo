package com.jvlcode.spring_boot_demo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jvlcode.spring_boot_demo2.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
