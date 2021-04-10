package com.example.log.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Table(name="log")
@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString

public class Log {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	@Column(length = 60)
	private String url;
	@Column(length = 10)
	private String method;
	@Column(length = 20)
	private String ip;
	@Column(length = 30)
	private String args;
	@Column(length = 255)
	private String beforefixing;
	@Column(length = 255)
	private String afterModification;



}
