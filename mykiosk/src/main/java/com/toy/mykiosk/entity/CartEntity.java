package com.toy.mykiosk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cart")
public class CartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id_pk")
	private Integer cart_id_pk;
	
	@Column(name = "menu_id_fk")
	private Integer menu_id_fk;
	
	@Column(name = "menu_cnt")
	private Integer menu_cnt;
}
