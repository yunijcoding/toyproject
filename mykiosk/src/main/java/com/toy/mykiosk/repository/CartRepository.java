package com.toy.mykiosk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toy.mykiosk.entity.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long>{
	
	@Query(value = "insert into cart(menu_id_fk)"
			+ "	values(:menu_id)", nativeQuery = true)
	@Modifying
	public void addToCart(@Param(value = "menu_id") Integer menu_id);
}
