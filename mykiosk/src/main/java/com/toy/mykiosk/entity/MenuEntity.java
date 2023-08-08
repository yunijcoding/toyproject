package com.toy.mykiosk.entity;

import com.toy.mykiosk.entity.MenuEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "menu")
public class MenuEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_pk")
	private Integer menu_pk;
	
	@Column(name = "menu_name")
	private String menu_name;
	
	@Column(name = "menu_price")
	private Integer menu_price;
	
	@Column(name = "menu_info")
	private String menu_info;
	
	@Column(name = "menu_img")
	private String menu_img;
}
