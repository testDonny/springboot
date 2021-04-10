package com.example.user.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "graphicscardall")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString

public class GraphicsCardAll implements Serializable{

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	/**
	 * 名稱
	 */
	@Column(length = 20)
	private String name;
	/**
	 * 礦池算力
	 * 八卡
	 */
	@Column(length = 10)
	private BigDecimal poolcomputingpower;
	/**
	 * 礦池算力
	 * 單卡
	 */
	@Column(length = 10)
	private BigDecimal poolcomputingpower8;

	/**
	 * 價格
	 */
	@Column(length = 10)
	private Integer price;

	/**
	 * 全網算力
	 */
	@Column(length = 30)
	private BigDecimal wholenetworkcomputingpower;

	/**
	 * 幣價
	 */
	@Column(length = 30)
	private BigDecimal currencyprice;

	/**
	 * 日理論收益
	 * 八卡 
	 */
	@Column(columnDefinition = "Decimal(10,3) default '0.000'")
	private BigDecimal dailytheoreticalreturn;
	/**
	 * 日理論收益
	 * 單卡 
	 */
	
	@Column(columnDefinition = "Decimal(10,3) default '0.000'")
	private BigDecimal dailytheoreticalreturn8;

	/**
	 * 演算法
	 * 
	 */
	@Column(length = 30)
	private String algorithm;

	/**
	 * 說明
	 */
	@Column(length = 10)
	private String description;
	
	/**
	 * 存貨
	 */
	@Column(length = 10)
	private Integer instock;

	/**
	 * 電費
	 * 八卡
	 */
	@Column(columnDefinition = "Decimal(6,2) default '0.00'")
	private BigDecimal electricity;
	/**
	 * 電費
	 * 單卡
	 */
	@Column(columnDefinition = "Decimal(6,2) default '0.00'")
	private BigDecimal electricity8;

	/**
	 * 電力瓦數
	 * 單
	 * 
	 */
	@Column(length = 10)
	private Integer powerwattage;
//	/**
//	 * 電力瓦數
//	 * 全部
//	 * 
//	 */
//	
//	@Column(length = 10)
//	private Integer powerwattages;


}
