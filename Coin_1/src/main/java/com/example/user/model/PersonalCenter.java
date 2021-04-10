package com.example.user.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.CascadeType;

@Entity
@Table(name = "PersonalCenter", indexes = { @Index(name = "email", columnList = "email", unique = true),
		@Index(name = "phone", columnList = "phone", unique = true) })
//@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalCenter implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
//	@GeneratedValue(strategy=GenerationType.IDENTITY)	//自動產生流水號
//	@GeneratedValue(strategy=GenerationType.SEQUENCE) 	//oracle 排序
//	@GeneratedValue(strategy=GenerationType.TABLE) 		//jpa 提供的,通過一張數據庫完成主鍵自增
//	@GeneratedValue(strategy=GenerationType.AUTO) 		//自動產生主鍵生成策略
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	/**
	 * 錢包地址
	 */
	@Column(length = 60)
	private String walletaddress;

	/**
	 * 信箱
	 */
	@Column(length = 60, nullable = false)
	private String email;

	/**
	 * 魚池錢包
	 */
//	@Column(length = 15, columnDefinition = "Decimal(10,2) default '0.00'")
	@Column(length = 20, columnDefinition = "int default 0")
	private int fishpondwallet;
//	private BigDecimal fishpondwallet = BigDecimal.ZERO;

	/**
	 * 綁定錢包
	 */
	@Column(length = 15, columnDefinition = "Decimal(10,2) default '0.00'")
	private BigDecimal boundwallet = BigDecimal.ZERO;

	/**
	 * 虛擬貨幣
	 */
	@Column(length = 15, columnDefinition = "Decimal(10,2) default '0.00'")
	private BigDecimal usdtsunamt = BigDecimal.ZERO;

	/**
	 * 手機
	 */
	@Column(length = 30)
	private String phone;

	/**
	 * 密碼
	 */
	@Column(length = 30)
	private String password;
	
	/**
	 * 註冊時間
	 */
	@Column(length = 30)
	@CreatedDate
	private Long registrationtime;
	
	/**
	 * 註冊時間
	 * 中文
	 */
	@Column(length = 30)
	private String 	registrationtime1;

	/**
	 * mappedBy 放棄外鍵 購買礦機紀錄
	 */
	@OneToMany(mappedBy = "personalCenter", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<Payment> payment = new HashSet<>();

	
	/**
	 * 狀態
	 */
	@Column(length = 10)
	private String status;
	/**
	 * 使用者卡池 顯示卡資訊
	 */

	@OneToMany(mappedBy = "personalCenter", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<UserCard> usercard = new HashSet<>();
//	/**
//	 * 儲值紀錄
//	 */
//	@OneToMany(mappedBy = "personalCenter", orphanRemoval = true, cascade = CascadeType.ALL)
//	private Set<Storedvalue> storedvalue = new HashSet<>();

//	/**
//	 * 驗證手機
//	 */
//	@OneToMany(mappedBy = "personalCenter", orphanRemoval = true, cascade = CascadeType.ALL)
//	private Set<VerificationPhone> verificationphone = new HashSet<>();
	/**
	 * 驗證
	 */
	@OneToMany(mappedBy = "personalCenter", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<Verification> verification;

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
		builder.appendSuper(super.toString());
		builder.append("id", id);
		builder.append("walletaddress", walletaddress);
		builder.append("email", email);
		builder.append("fishpondwallet", fishpondwallet);
		builder.append("boundwallet", boundwallet);
		builder.append("usdtsunamt", usdtsunamt);
		builder.append("phone", phone);
		builder.append("password", password);
		builder.append("payment", payment);
		builder.append("usercard", usercard);
//		builder.append("verificationphone", verificationphone);
		builder.append("verification", verification);

		return builder.toString();
	}

}
