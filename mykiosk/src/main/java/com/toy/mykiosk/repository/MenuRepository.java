package com.toy.mykiosk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toy.mykiosk.entity.MenuEntity;

public interface MenuRepository extends JpaRepository<MenuEntity, Long>{

}
