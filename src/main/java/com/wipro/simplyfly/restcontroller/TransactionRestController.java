package com.wipro.simplyfly.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.simplyfly.dto.TransactionResponseDTO;
import com.wipro.simplyfly.service.ITransactionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/simplyfly/")
public class TransactionRestController {
   @Autowired
   ITransactionService transactionService;
   //get transaction by id
   @GetMapping("TransactionById/{bookingId}")
   public TransactionResponseDTO getTransactionByID(@PathVariable long bookingId) {
	   return transactionService.getTransaction(bookingId);
	   
   }
}
