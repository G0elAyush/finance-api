package com.myapp.restapi.mfm.txn;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

//@Entity
//@Table(name = "txn_dtls")
@Data
@JsonPropertyOrder({ "id", "userName" ,"firstName","lastName","email","phone"})
public class Transaction {
	
	private long id;
	private long user;
	private long category;
	private long subCategory;
	private long account;
	private String currency;
	private BigDecimal amount;
	private Date date;
	private int time;
	private String note;

}
