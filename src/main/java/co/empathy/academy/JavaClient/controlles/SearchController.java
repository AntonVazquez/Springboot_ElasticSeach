package co.empathy.academy.JavaClient.controlles;


import co.empathy.academy.JavaClient.exception.RecordNotFoundException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

        @Autowired
        private SearchService searchService;




    /*
    @GetMapping("/search")
    public ResponseEntity<Response> getMovies(   @RequestParam(required = false) String [] genres,
                                                 @RequestParam(required = false) Integer maxYear,
                                                 @RequestParam(required = false) Integer minYear,
                                                 @RequestParam(required = false) Integer maxMinutes,
                                                 @RequestParam(required = false) Integer minMinutes,
                                                 @RequestParam(required = false) Double maxScore,
                                                 @RequestParam(required = false) Double minScore,
                                                 @RequestParam(required = false) String[] type) throws IOException {

        Filters filter=Filters.builder()
                .type(type)
                .maxMinutes(maxMinutes)
                .minMinutes(minMinutes)
                .maxYear(maxYear)
                .minYear(minYear)
                .genre(genres)
                .minScore(minScore)
                .maxScore(maxScore)
                .build();

        return new ResponseEntity<>(searchService.getQuery(filter),HttpStatus.ACCEPTED);
    }



    @GetMapping("/search/text")
    public ResponseEntity<Response> getSearchMovies( @RequestParam(required = true) String searchText) throws IOException {
        System.out.println(searchText);
        return new ResponseEntity<>(searchService.getSearchQuery(searchText),HttpStatus.ACCEPTED);
    }

     */





}




