package io.neeraj.movieinfoservice.resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.neeraj.movieinfoservice.model.Movie;

@RestController
@RequestMapping("/movies")
public class MovieResource {

	@RequestMapping("/{movieId}")
	public Movie getMovie(@PathVariable String movieId) {
		return new Movie("1", "2 Fast 2 Furious");
	}
}
