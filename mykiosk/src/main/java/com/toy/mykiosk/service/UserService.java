package com.toy.mykiosk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.mykiosk.entity.QUserEntity;
import com.toy.mykiosk.entity.UserEntity;

@Service
public class UserService {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	//user 정보 조회
	public List<UserEntity> getUser(String user_number) {
		
		QUserEntity qUser = QUserEntity.userEntity;
		
		return jpaQueryFactory
				.selectFrom(qUser)
				.where(qUser.user_number.eq(user_number))
				.fetch();
	}
}
