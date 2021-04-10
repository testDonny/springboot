package com.example.user.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usercard")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class UserCard implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	@Column(length = 20)
	private String card1;
	@Column(length = 20)
	private String card2;
	@Column(length = 20)
	private String card3;
	@Column(length = 20)
	private String card4;
	@Column(length = 20)
	private String card5;
	@Column(length = 20)
	private String card6;
	@Column(length = 20)
	private String card7;
	@Column(length = 20)
	private String card8;

	/**
	 * 位置狀態
	 */
	@Column(length = 5)
	private String locationstatus;

	/**
	 * 礦機編號
	 */
	@Column(length = 5)
	private String miningmachinenumber;

	/**
	 * 礦機 所在區域
	 */
	@Column(length = 5)
	private String area;

	/**
	 * mappedBy 放棄外鍵 購買礦機紀錄
	 */
	@OneToMany(mappedBy = "userCard", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<CardStatus> cardStatu = new HashSet<>();

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email_id", referencedColumnName = "email")
	private PersonalCenter personalCenter;

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
		builder.appendSuper(super.toString());

		builder.append("id", id);
		builder.append("card1", card1);
		builder.append("card2", card2);
		builder.append("card3", card3);
		builder.append("card4", card4);
		builder.append("card5", card5);
		builder.append("card6", card6);
		builder.append("card7", card7);
		builder.append("card8", card8);
		builder.append("miningmachinenumber", miningmachinenumber);
		builder.append("area", area);
		builder.append("cardStatu", cardStatu);
		builder.append("personalCenter", personalCenter);

		return builder.toString();
	}

}
