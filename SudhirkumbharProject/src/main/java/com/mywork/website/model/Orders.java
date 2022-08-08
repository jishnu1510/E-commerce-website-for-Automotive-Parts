package com.mywork.website.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="orders")
public class Orders 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long myOrderId;


	private String orderId;
	
	private double amount;
	
	private String receipt;
	
	private String status;
	
	private String paymentId;
	
	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getMyOrderId() {
		return myOrderId;
	}

	public void setMyOrderId(Long myOrderId) {
		this.myOrderId = myOrderId;
	}

	public String getOrderid() {
		return orderId;
	}

	public void setOrderid(String orderid) {
		this.orderId = orderid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public double get(String string) {
		// TODO Auto-generated method stub
		
		return this.amount;
	}
	
	
	
}
