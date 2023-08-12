package com.toy.mykiosk.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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
				//count(menu_id_pk) menu_cnt => qCart.count().as("menu_cnt")
				.select(Projections.fields(MenuCartDTO.class,
						qCart.menu_id_fk, qMenu.menu_name, qMenu.menu_price,
						qCart.menu_id_fk.count().as("menu_cnt"), qMenu.menu_img, qMenu.menu_info, qCart.cart_id_pk))
				//from 메서드에는 엔티티가 아닌 프로젝션을 사용해야 함
//				.from(qCart)
//				.innerJoin(qMenu).on(qMenu.menu_id_pk.eq(qCart.menu_id_fk))
				.from(qCart, qMenu)
				.where(qCart.menu_id_fk.eq(qMenu.menu_id_pk))
				.groupBy(qMenu.menu_id_pk, qMenu.menu_name, qMenu.menu_price, qMenu.menu_img, qMenu.menu_info, qCart.cart_id_pk)
				.fetch();
	}
	
	
	//cart와 menu join! => count 도출 & group by
	public List<MenuCartDTO> getAllMenuJoinCartConstructor(){
		QCartEntity qCart = QCartEntity.cartEntity;
		QMenuEntity qMenu = QMenuEntity.menuEntity;
		
		return jpaQueryFactory
				.select(Projections.constructor(MenuCartDTO.class,
						qCart.menu_id_fk, qMenu.menu_name, qMenu.menu_price,
						qCart.menu_id_fk.count().as("menu_cnt"), qMenu.menu_img, qMenu.menu_info))
				.from(qCart)
				.innerJoin(qMenu).on(qMenu.menu_id_pk.eq(qCart.menu_id_fk))
				.groupBy(qCart.menu_id_fk, qMenu.menu_name, qMenu.menu_price, qMenu.menu_img, qMenu.menu_info)
				.fetch();
	}
	
	// cart delete
	@Transactional
	public void deleteToCart(Integer menu_id) {
		QCartEntity qCart = QCartEntity.cartEntity;
		
		jpaQueryFactory
		.delete(qCart)
		.where(qCart.menu_id_fk.eq(menu_id))
		.execute();
	}
	
	// minus btn 처리
	// 1) 우선 menu_id가 여러개일 수 있으므로 menu_id 중에 첫번째 cart_id_pk 뽑기
	public Integer getOneCartId(Integer menu_id) {
		QCartEntity qCart = QCartEntity.cartEntity;
		
		return jpaQueryFactory
				.select(qCart.cart_id_pk)
				.from(qCart)
				.where(qCart.menu_id_fk.eq(menu_id))
				.limit(1)
				.fetchFirst();
	}
	
	// 2) 추출한 cart_id_pk 값으로 delete 하기
	@Transactional
	public void deleteCartId(Integer cart_id) {
		QCartEntity qCart = QCartEntity.cartEntity;
		
		jpaQueryFactory
		 .delete(qCart)
		 .where(qCart.cart_id_pk.eq((cart_id)))
		 .execute();
	}
	
	
	//cart의 가격 총 합계 구하기
//	public Long totPrice() {
//		QCartEntity qCart = QCartEntity.cartEntity;
//		QMenuEntity qMenu = QMenuEntity.menuEntity;
//		
//		List<MenuCartDTO> sub = jpaQueryFactory
//									.select(Projections.fields(MenuCartDTO.class, qMenu.menu_price.sum().as("totalPrice")))
//									.from(qCart)
//									.innerJoin(qMenu).on(qMenu.menu_id_pk.eq(qCart.menu_id_fk))
//									.groupBy(qCart.menu_id_fk, qMenu.menu_name, qMenu.menu_price, qMenu.menu_img, qMenu.menu_info)
//									.fetch();
//		
//		return jpaQueryFactory
//				.select(sub.totalPrice.sum())
//				.from(sub)
//	}
	
//	public Long calculateTotalSum() {
//		QCartEntity qCart = QCartEntity.cartEntity;
//		QMenuEntity qMenu = QMenuEntity.menuEntity;
//
//        // 서브쿼리 생성
//        List<Integer> subQuery = jpaQueryFactory
//                .select(qMenu.menu_price.sum().as("sum"))
//                .from(qMenu, qCart)
//                .where(qMenu.menu_id_pk.eq(qCart.menu_id_fk))
//                .groupBy(qCart.menu_id_fk)
//                .fetch();
//                
//        // 메인 쿼리
//        Long totalSum = jpaQueryFactory
//                .select(subQuery.sum())
//                .from(subQuery)
//                .fetchOne();
//
//        return totalSum;
//    }
	
	
	public Long getTotalPrice() {
		return this.cartRepository.getTotalPrice();
	}
}

















