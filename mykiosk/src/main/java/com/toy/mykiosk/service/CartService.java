package com.toy.mykiosk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.mykiosk.dto.MenuCartDTO;
import com.toy.mykiosk.entity.CartEntity;
import com.toy.mykiosk.entity.QCartEntity;
import com.toy.mykiosk.entity.QMenuEntity;
import com.toy.mykiosk.repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	private CartRepository cartRepository;
	
	// main list에서 add to cart 버튼 클릭 시 cart 테이블에 insert
	// queryDSL에서 insert 문제 발생
//	public void addToCart(Integer menu_id){
//		QCartEntity qCart = QCartEntity.cartEntity;
//		
//		jpaQueryFactory
//			.insert(qCart)
//			.set(qCart.menu_id_fk, menu_id)
//			.execute();
//	}
	
	//insert 하려면 @Transactional 어노테이션이 필요함..
	@Transactional
	public void addToCart(Integer menu_id) {
		this.cartRepository.addToCart(menu_id);
	}
	
	//(test) cart 전체 목록
	public List<CartEntity> getAllCart(){
		QCartEntity qCart = QCartEntity.cartEntity;
		
		return jpaQueryFactory
				.selectFrom(qCart)
				.fetch();
	}
	
	// menu, cart 테이블 join 후 조회
	public List<MenuCartDTO> getAllMenuJoinCart(){
		QCartEntity qCart = QCartEntity.cartEntity;
		QMenuEntity qMenu = QMenuEntity.menuEntity;
		
		return jpaQueryFactory
				//Projections.constructor를 사용하여 MenuCartDTO를 생성
				//중복을 허용하지 않으려면 Projections.bean 사용
				//MenuCartDTO 생성자의 변수 순서랑 일치해야 한다!!!!!
				.select(Projections.constructor(MenuCartDTO.class,
						qCart.menu_id_fk, qMenu.menu_name, qMenu.menu_price,
	                    qCart.menu_cnt, qMenu.menu_img, qMenu.menu_info, qCart.cart_id_pk))
				//from 메서드에는 엔티티가 아닌 프로젝션을 사용해야 함
				.from(qCart)
				.join(qMenu).on(qMenu.menu_id_pk.eq(qCart.menu_id_fk))
				.fetch();
	}
}

















