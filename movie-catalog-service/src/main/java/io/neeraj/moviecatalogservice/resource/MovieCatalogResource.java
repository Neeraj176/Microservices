package io.neeraj.moviecatalogservice.resource;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.neeraj.moviecatalogservice.model.CatalogItem;
import io.neeraj.moviecatalogservice.model.UserRating;
import io.neeraj.moviecatalogservice.service.MovieInfo;
import io.neeraj.moviecatalogservice.service.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		UserRating userRating = userRatingInfo.getUserRating(userId);
		return userRating.getUserRating().stream()
				.map(rating -> movieInfo.getCatalogItem(rating))
				.collect(Collectors.toList());
	}
}
