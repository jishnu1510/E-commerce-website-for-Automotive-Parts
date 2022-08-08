package com.mywork.website.controller;

import org.json.JSONObject;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.mywork.website.global.GlobalData;
import com.mywork.website.model.Product;
import com.mywork.website.model.shippingdetails;
import com.mywork.website.service.ProductService;

import com.mywork.website.model.Orders;

import com.mywork.website.repository.OrdersRepository;
import com.mywork.website.repository.OrdershippingRepository;
import com.mywork.website.repository.UserRepository;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrdershippingRepository ordershippingRepository;
    
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id) {
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }
    @GetMapping("/cart")
    public String cartGet(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart",GlobalData.cart);
        return "cart";
    }
    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index) {
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }
    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }
    
    @PostMapping("/cart")
    public String shippingsave(@ModelAttribute shippingdetails save,Model model)
    {
    	
    	System.out.println(save);
    	ordershippingRepository.save(save);
    	 model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
    	return "checkout";
    }

    
    
	@PostMapping("/checkout/create_order")
	@ResponseBody
	public String Payment(@RequestBody Map<String, Object> data) throws RazorpayException
	{
		System.out.println(data);
		
		double amt=Double.parseDouble(data.get("amount").toString());
		var client=new RazorpayClient("rzp_test_4Bal0tYCaBPUg7", "oVQxWWAPsevlaSuE2kthd4yV");
		
		JSONObject orderRequest = new JSONObject();
		  orderRequest.put("amount", amt*100); // amount in the smallest currency unit
		  orderRequest.put("currency", "INR");
		  orderRequest.put("receipt", "order_rcptid_11");

		  Order order = client.orders.create(orderRequest);
		  System.out.println(order);
		  
		  Orders orders=new Orders();
		  orders.setAmount(amt);
		  orders.setOrderid(order.get("id"));
		  orders.setPaymentId(null);
		  orders.setStatus("created");
		  orders.setReceipt(order.get("receipt"));
		  
		  
		  
		  this.ordersRepository.save(orders);
		
		
		return order.toString();
		
	}
	@PostMapping("/checkout/update_order")
	public ResponseEntity<?> updateOrder(@RequestBody Map<String,Object> data)
	{
		GlobalData.cart.clear();
		Orders orders=this.ordersRepository.findByOrderId(data.get("order_id").toString());
		orders.setPaymentId(data.get("payment_id").toString());
		orders.setStatus(data.get("status").toString());
		this.ordersRepository.save(orders);
		System.out.println(data);
		return ResponseEntity.ok(Map.of("msg","updated"));
	}
    
}
