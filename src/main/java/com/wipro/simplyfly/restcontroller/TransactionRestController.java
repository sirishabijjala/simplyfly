package com.wipro.simplyfly.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.simplyfly.dto.TransactionResponseDTO;
import com.wipro.simplyfly.service.ITransactionService;

@RestController
@RequestMapping("/simpyfly/")
public class TransactionRestController {
   @Autowired
   ITransactionService transactionService;
   //get transaction by id
   @GetMapping("TransactionById")
   public TransactionResponseDTO getTransactionByID(Long id) {
	   return transactionService.getTransaction(id);
	   
   }
}
