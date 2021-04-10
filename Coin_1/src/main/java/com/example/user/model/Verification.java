package com.example.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "verification")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Verification implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	@Column(length = 10)
	private String codeEmail;

	/**
	 * 註冊狀態預設為0
	 */
	@Column(length = 1, columnDefinition = "int default 0")
	private int statusPhone;
	
	@Column(length = 30)
	private Long phoneTime;

	@Column(length = 10)
	private String codePhone;

	@Column(length = 30)
	private Long emailTime;
	/**
	 * 註冊狀態預設為0
	 */
	@Column(length = 1, columnDefinition = "int default 0")
	private int statusEmail;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email_id", referencedColumnName = "email")
	private PersonalCenter personalCenter;

}
