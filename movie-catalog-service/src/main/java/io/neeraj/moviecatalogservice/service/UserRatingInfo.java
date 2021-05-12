package io.neeraj.moviecatalogservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.neeraj.moviecatalogservice.model.Rating;
import io.neeraj.moviecatalogservice.model.UserRating;

@Service
public class UserRatingInfo {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="getFallbackUserRating",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
					@HystrixProperty(name = "circutBreaker.requestVolumeThreshold", value = "5"),
					@HystrixProperty(name = "circutBreaker.errorThresholdPercentage", value = "50"),
					@HystrixProperty(name = "circutBreaker.sleepWindowInMilliseconds", value = "5000")
			},
			threadPoolKey = "userRatingPool",
			threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "20"),
					@HystrixProperty(name = "maxQueueSize", value = "10")
			}
	)
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://MOVIE-RATING-SERVICE/ratings/users/" + userId, UserRating.class);
	}

	public UserRating getFallbackUserRating(@PathVariable String userId){
		UserRating userRating = new UserRating();
		userRating.setUserRating(Arrays.asList(
				new Rating("0", 0)
				));
		return userRating;
	}
}
