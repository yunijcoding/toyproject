package com.toy.mykiosk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toy.mykiosk.entity.MenuEntity;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long>{
	
}
