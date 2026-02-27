package com.wipro.simplyfly.service;

import com.wipro.simplyfly.dto.TransactionRequestDTO;
import com.wipro.simplyfly.dto.TransactionResponseDTO;

public interface ITransactionService {
	 TransactionResponseDTO makePayment(Long bookingId,
             TransactionRequestDTO request);

TransactionResponseDTO getTransaction(Long id);
TransactionResponseDTO refundPayment(Long bookingId);

}
