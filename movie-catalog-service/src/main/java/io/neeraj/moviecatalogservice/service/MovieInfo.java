package io.neeraj.moviecatalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.neeraj.moviecatalogservice.model.CatalogItem;
import io.neeraj.moviecatalogservice.model.Movie;
import io.neeraj.moviecatalogservice.model.Rating;

@Service
public class MovieInfo {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="getFallbackCatalogItem",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
					@HystrixProperty(name = "circutBreaker.requestVolumeThreshold", value = "5"),
					@HystrixProperty(name = "circutBreaker.errorThresholdPercentage", value = "50"),
					@HystrixProperty(name = "circutBreaker.sleepWindowInMilliseconds", value = "5000")
			},
			threadPoolKey = "movieInfoPool",
			threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "20"),
					@HystrixProperty(name = "maxQueueSize", value = "10")
			}
	)
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName() ,"Desc", rating.getRating());
	}
	
	public CatalogItem getFallbackCatalogItem(Rating rating){
		return new CatalogItem("No Movie Name", "No Description", 1);
	}
	
}
