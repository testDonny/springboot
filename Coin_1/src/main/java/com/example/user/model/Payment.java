package com.example.user.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Payment implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email_id", referencedColumnName = "email")
	private PersonalCenter personalCenter;

	/**
	 * 收款地址
	 */
	@Column(length = 60)
	private String paymentaddress;

	/**
	 * 收款金額
	 */
	@Column(length = 20)
	private int amountreceived;

	/**
	 * 這筆交易的時間
	 */

	@Column(length = 30)
	@CreatedDate
	private Long consumptiontime;

	/**
	 * 這筆交易的時間
	 */
	@Column(length = 30)
	private String time;

	/**
	 * 交易狀態 0為購買 1為儲值 2為提領
	 */
	@Column(length = 5)
	private String status;

	/**
	 * 省核通過時間
	 */
	@Column(length = 30)
	@CreatedDate
	private Long provincialapprovaltime;

	/**
	 * 這筆交易省核通過的時間
	 */
	@Column(length = 30)
	private String times;
	
	/**
	 * 原本金額
	 */
	
	@Column(length = 30)
	private Integer originalamount;
	
	
	/**
	 * 交易後
	 * 金額
	 */
	
	@Column(length = 30)
	private Integer amountaftertransaction;

	/**
	 * 審核狀態
	 * 0未通過
	 * 1通過
	 * 2不通過
	 */
	@Column(length = 1)
	private String approvalstatus;

	/**
	 * 日理論收益
	 */
	@Column(length = 15, columnDefinition = "Decimal(10,2) default '0.00'")
	private BigDecimal dailytheoreticalreturn;

	/**
	 * 總瓦數
	 */
	@Column(length = 10)
	private Integer powerwattage;

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
		builder.appendSuper(super.toString());

		builder.append("id", id);
		builder.append("paymentaddress", paymentaddress);

		builder.append("amountreceived", amountreceived);
		builder.append("consumptiontime", consumptiontime);

		builder.append("provincialapprovaltime", provincialapprovaltime);

		builder.append("approvalstatus", approvalstatus);
		builder.append("dailytheoreticalreturn", dailytheoreticalreturn);
		builder.append("powerwattage", powerwattage);

		return builder.toString();
	}

}
