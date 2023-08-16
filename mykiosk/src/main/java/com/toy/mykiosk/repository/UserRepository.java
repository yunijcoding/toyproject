package com.toy.mykiosk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toy.mykiosk.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

}
