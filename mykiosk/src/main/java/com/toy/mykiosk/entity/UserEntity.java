package com.toy.mykiosk.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id_pk")
	private Integer user_id_pk;
	
	@Column(name = "user_name")
	private String user_name;
	
	@Column(name = "user_number")
	private String user_number;
	
	@Column(name = "user_birth")
	private String user_birth;
	
	@Column(name = "user_stamp")
	private Integer user_stamp;
	
	@Column(name = "regidate")
	private Date regidate;
}
