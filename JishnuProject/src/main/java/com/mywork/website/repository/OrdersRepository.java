package com.mywork.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mywork.website.model.Orders;

//import FirstSTS.Model.Orders;

public interface OrdersRepository extends JpaRepository<Orders,Long> 
{
	public Orders findByOrderId(String orderId); 

}
