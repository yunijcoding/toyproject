package com.toy.mykiosk.dto;

import lombok.Data;

@Data
public class MenuCartDTO {
	
	private Integer menu_id_fk;
	private String menu_name;
	private Integer menu_price;
	private Integer menu_cnt;
	private String menu_img;
	private String menu_info;
	private Integer cart_id_pk;
	
	public MenuCartDTO(Integer menu_id_fk, String menu_name, Integer menu_price, Integer menu_cnt, String menu_img,
			String menu_info, Integer cart_id_pk) {

		this.menu_id_fk = menu_id_fk;
		this.menu_name = menu_name;
		this.menu_price = menu_price;
		this.menu_cnt = menu_cnt;
		this.menu_img = menu_img;
		this.menu_info = menu_info;
		this.cart_id_pk = cart_id_pk;
	}
}
