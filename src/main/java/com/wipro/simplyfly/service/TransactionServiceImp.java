package com.wipro.simplyfly.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.TransactionRequestDTO;
import com.wipro.simplyfly.dto.TransactionResponseDTO;
import com.wipro.simplyfly.entity.Booking;
import com.wipro.simplyfly.entity.Transaction;
import com.wipro.simplyfly.repository.BookingRepository;
import com.wipro.simplyfly.repository.TransactionRepository;

import jakarta.transaction.Transactional;
@Service
public class TransactionServiceImp implements ITransactionService{
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	BookingRepository bookingRepository;

	@Override
	@Transactional
	public TransactionResponseDTO makePayment(Long bookingId, TransactionRequestDTO request) {
		Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("Booking Not Found"));
		Transaction transaction=new Transaction();
		transaction.setTransactionId(UUID.randomUUID().toString());
		transaction.setBooking(booking);
		transaction.setAmount(booking.getTotalAmount());
		transaction.setPaymentMethod(request.getPaymentMethod());
		transaction.setPaymentStatus("CONFIRMED");
		transaction.setTransactionDate(LocalDateTime.now());
		Transaction saved=transactionRepository.save(transaction);
		booking.setBookingStatus("CONFIRMED");
		bookingRepository.save(booking);
		
		return  mapToResponse(saved);
	}

	@Override
	public TransactionResponseDTO getTransaction(Long id) {
		Transaction transaction=transactionRepository.findById(id).orElseThrow(()-> new RuntimeException("Transaction Not Found"));
		
		return mapToResponse(transaction);
	}
	public TransactionResponseDTO mapToResponse(Transaction transaction) {
		TransactionResponseDTO response=new TransactionResponseDTO();
		response.setAmount(transaction.getAmount());
		response.setTransactionId(transaction.getTransactionId());
		response.setPaymentStatus(transaction.getPaymentStatus());
		response.setPaymentMethod(transaction.getPaymentMethod());
		response.setBookingId(transaction.getBooking().getId());
		response.setTransactionDate(transaction.getTransactionDate());
		return response;
	}
		
		
		@Override
		@Transactional
		public TransactionResponseDTO refundPayment(Long bookingId) {
		    // Find the transaction by booking
		    Transaction transaction = transactionRepository.findByBookingId(bookingId)
		            .orElseThrow(() -> new RuntimeException("Transaction not found for this booking"));

		    // Only refund if payment was successful
		    if (!"CONFIRMED".equals(transaction.getPaymentStatus())) {
		        throw new RuntimeException("Payment not eligible for refund");
		    }

		    transaction.setPaymentStatus("REFUNDED");
		    transaction.setTransactionDate(LocalDateTime.now()); // Update refund timestamp

		    Transaction saved = transactionRepository.save(transaction);

		    return mapToResponse(saved);
			}


	

}
