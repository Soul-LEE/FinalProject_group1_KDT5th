package com.exam.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.config.ReviewsMapper;
import com.exam.config.UsersMapper;
import com.exam.dto.CartsDTO;
import com.exam.dto.UsersDTO;
import com.exam.dto.UsersDTO.UsersModifyDTO;
import com.exam.entity.Carts;
import com.exam.entity.DeliveryAddress;
import com.exam.entity.Reviews;
import com.exam.entity.Users;
import com.exam.repository.CartsRepository;
import com.exam.repository.DeliveryAddressRepository;
import com.exam.repository.ReviewsRepository;
import com.exam.repository.UsersRepository;

import ch.qos.logback.core.encoder.Encoder;

@Service
@Transactional
public class ReviewsServiceImpl implements ReviewsService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	ReviewsRepository reviewsRepository;
	ReviewsMapper reviewsMapper;

	
	
	public ReviewsServiceImpl(ReviewsRepository reviewsRepository, ReviewsMapper reviewsMapper) {
		this.reviewsRepository = reviewsRepository;
		this.reviewsMapper = reviewsMapper;
	}

	@Override
	public Reviews saveReviews(Reviews reviews) {
		
		return reviewsRepository.save(reviews);
	}
	
	@Override
	public List<Integer> getReviewedOrderDetailIds(int userId, int orderId) {
		
		try {
			Map<String, Integer> params = new HashMap<>();
			params.put("userId", userId);
			params.put("orderId", orderId);
			
			List<Integer> orderDetailIds = reviewsMapper.findOrderDetailIdByUserId(params);
		
			return orderDetailIds;
		}catch (Exception e) {
	        logger.error("Error retrieving reviewed order detail IDs for userId: {}, orderId: {}", userId, orderId, e);
	        throw e; 
	    }
	}
	
	@Override
	public List<Integer> findReviewedOrderDetailIds(int userId) {
		return reviewsMapper.findOrderDetailIdsByUserId(userId);
	}
}