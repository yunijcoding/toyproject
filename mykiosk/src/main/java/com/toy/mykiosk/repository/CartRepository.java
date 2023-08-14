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
	
	
	//cart에서 menu의 total price
	@Query(value = "select sum(sub.sum) "
			+ "from "
			+ "(select sum(menu_price) as sum "
			+ "from menu m, cart c "
			+ "where m.menu_id_pk = c.menu_id_fk "
			+ "group by menu_id_fk) sub", nativeQuery = true)
	public Long getTotalPrice();
	
	
}
