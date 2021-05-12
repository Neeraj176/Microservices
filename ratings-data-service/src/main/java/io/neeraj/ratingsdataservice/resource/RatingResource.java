package io.neeraj.ratingsdataservice.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.neeraj.ratingsdataservice.model.Rating;
import io.neeraj.ratingsdataservice.model.UserRating;

@RestController
@RequestMapping("/ratings")
public class RatingResource {

	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable String movieId) {
		return new Rating(movieId, 4);
	}
	
	@RequestMapping("users/{userId}")
	public UserRating getUserRating(@PathVariable String userId) {
		List<Rating> ratings = Arrays.asList(
				new Rating("1", 4),
				new Rating("2", 3)
		);		
		
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		return userRating;
	}
}
