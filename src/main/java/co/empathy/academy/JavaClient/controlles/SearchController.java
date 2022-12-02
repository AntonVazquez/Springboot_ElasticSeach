package co.empathy.academy.JavaClient.controlles;


import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.services.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class SearchController {

        @Autowired
        private SearchServiceImpl searchService;

    public SearchController(SearchServiceImpl searchService) {
        this.searchService = searchService;
    }







    @GetMapping("/search")
    public ResponseEntity<List<Movie>> getMovies(@RequestParam(required = false) Optional<String> genres,
                                                 @RequestParam(required = false) Optional<Integer> maxYear,
                                                 @RequestParam(required = false) Optional<Integer> minYear,
                                                 @RequestParam(required = false) Optional<Integer> maxMinutes,
                                                 @RequestParam(required = false) Optional<Integer> minMinutes,
                                                 @RequestParam(required = false) Optional<Double> maxScore,
                                                 @RequestParam(required = false) Optional<Double> minScore,
                                                 @RequestParam(required = false) Optional<Integer> maxNHits,
                                                 @RequestParam(required = false) Optional<String> type) throws IOException {

        List<Movie> sr = searchService.search(genres, maxYear, minYear, maxMinutes, minMinutes, maxScore, minScore,maxNHits, type);
        return ResponseEntity.ok().body(sr);


    }



}




