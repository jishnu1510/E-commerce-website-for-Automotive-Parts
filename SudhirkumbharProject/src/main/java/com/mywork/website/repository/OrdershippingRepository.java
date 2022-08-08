package com.mywork.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mywork.website.model.shippingdetails;

public interface OrdershippingRepository extends JpaRepository<shippingdetails, Integer> {


}
