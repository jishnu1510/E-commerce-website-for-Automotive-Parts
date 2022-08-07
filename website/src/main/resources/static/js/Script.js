/**
 * 
 */
 //first request to server tocreate order
 const paymentStart=()=>
    {
	console.log("payment started...");
	let amount = document.getElementById("main").innerHTML;
	console.log(amount);
	if(amount=='' || amount==null)
	{
		//alert("amount is required");
		swal("Failed !", "amount is required", "error");
		return;
	};
	
	//wewilluse ajax to send request to server to create order
	$.ajax
	({
		url:"/checkout/create_order",
		data:JSON.stringify({amount:amount,info:"order_request"}),
		contentType:"application/json",
		type:"POST",
		dataType:"json",
		success:function(response)
		{
			console.log(response);
			if(response.status == "created")
			{
				let options={
					key:"rzp_test_4Bal0tYCaBPUg7",
					amount:response.amount,
					currency:"INR",
					name:"Auto Parts Plus",
					description:"Best Auto Parts",
					order_id:response.id,
					handler:function(response)
					{
						alert("Please Make a Note of payment details\nAnd click okay\n"
						+"payment-Id="+response.razorpay_payment_id+",\n"
						+"Order-Id="+response.razorpay_order_id+",\n");
						console.log(response.razorpay_signature)
						swal("Good job!", "congrats !! Payment Successful !! And data is stored", "success");

						
						updatePaymentOnServer(response.razorpay_payment_id,response.razorpay_order_id,"paid");
						
					
						
		
					},
					
					prefill: {
								"name": "",
								"email": "",
								"contact": "",
								},
					notes: {
								"address": "AutoPartsPlus,EdubridgeStreet,Salem",
								
								},
					theme: {
								"color": "#3399cc",
								},
													
				};
			
			let rzp=new Razorpay(options);
			rzp.on('payment.failed', function (response)
			{
				console.log(response.error.code);
				console.log(response.error.description);
				console.log(response.error.source);
				console.log(response.error.step);
				console.log(response.error.reason);
				console.log(response.error.metadata.order_id);
				console.log(response.error.metadata.payment_id);
				//alert("Oops Payment Failed");
				swal("Failed !", "Oops Payment Failed", "error");
			});
			rzp.open();
			}
		},
		error:function(error)
		{
			console.log(error);
			alert("something went wrong ");
		},

	});

};

//
 function updatePaymentOnServer(payment_id, order_id, status)
 {
    $.ajax
	({
		url:"/checkout/update_order",
		data:JSON.stringify({payment_id: payment_id,order_id: order_id,status: status,}),
		contentType:"application/json",
		type:"POST",
		dataType:"json",
		success: function(response)
		{
				alert("Redirecting to Another Page");
				window.location = "//localhost:8080/home";
		},
		error: function(error)
		{
			swal("Failed !", "Payment Done,But Some error", "error");
		},
	});
	
	
}