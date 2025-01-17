package com.exam.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.exam.config.ProductsMapper;
import com.exam.config.ReviewsMapper;
import com.exam.dto.ProductsDTO;
import com.exam.dto.ReviewsDTO;
import com.exam.dto.ReviewStatsDTO;
import com.exam.entity.Reviews;
import com.exam.repository.ReviewsRepository;

@Service
@Transactional
public class ReviewsServiceImpl implements ReviewsService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	ReviewsRepository reviewsRepository;
	ReviewsMapper reviewsMapper;
	UsersService usersService;
	ProductsMapper productsMapper;

	public ReviewsServiceImpl(ReviewsRepository reviewsRepository, ReviewsMapper reviewsMapper,
			UsersService usersService, ProductsMapper productsMapper) {
		this.reviewsRepository = reviewsRepository;
		this.reviewsMapper = reviewsMapper;
		this.usersService = usersService;
		this.productsMapper = productsMapper;
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
	
	@Override
	public List<ReviewsDTO> findReviews(int productId) {
		List<ReviewsDTO> reviews = reviewsMapper.findReviews(productId);
		for (ReviewsDTO review : reviews) {
	        String id = usersService.findIdByUserId(review.getUserId());
	        review.setId(id);
	    }
		return reviews;
	}
	
	@Override
	public ProductsDTO findProductDetails(int productId) {
		return productsMapper.findProductById(productId);
	}
	
	@Override
	public int findReviewsCounts(int productId) {
		return reviewsMapper.findReviewCounts(productId);
	}
	
	@Override
	public List<ReviewsDTO> findReivewsByUserId(int userId) {
		return reviewsMapper.findReviewsByUserId(userId);
	}
	
	@Override
	public boolean checkReview(int orderDetailId) {
		return reviewsMapper.existReview(orderDetailId);
	}
	
	@Override
	public ReviewStatsDTO getReviewStatsByProductId(int productId) {
		return reviewsMapper.findProductReview(productId);
	}
}
