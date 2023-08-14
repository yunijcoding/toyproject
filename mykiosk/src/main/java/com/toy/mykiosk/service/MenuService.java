package com.toy.mykiosk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.mykiosk.entity.MenuEntity;
import com.toy.mykiosk.entity.QCartEntity;
import com.toy.mykiosk.entity.QMenuEntity;
import com.toy.mykiosk.repository.MenuRepository;

@Service
public class MenuService {
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	public List<MenuEntity> getAll(){
		return this.menuRepository.findAll();
	}
	
	public List<MenuEntity> getAllMenu(){
		QMenuEntity menu = QMenuEntity.menuEntity;
		
		return jpaQueryFactory
				.selectFrom(menu)
				.fetch();
	}
	
	//menu_type에 따른 분류
	public List<MenuEntity> getAllMenu(String type){
		QMenuEntity menu = QMenuEntity.menuEntity;
		
		if(type.equals("all")) {
			return jpaQueryFactory
					.selectFrom(menu)
					.fetch();
		}
		else {
			return jpaQueryFactory
					.selectFrom(menu)
					.where(menu.menu_type.eq(type))
					.fetch();
		}
	}
	
	//queryDSL test: 2000원 이상인 음료 조회
	public List<MenuEntity> getMenuByPrice(Integer price){
		QMenuEntity menu = QMenuEntity.menuEntity;
		
		return jpaQueryFactory
				.selectFrom(menu)
				.where(menu.menu_price.gt(price)) //gt => greater than
				.fetch();
	}
}












