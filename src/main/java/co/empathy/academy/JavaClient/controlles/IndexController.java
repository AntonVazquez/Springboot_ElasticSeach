package co.empathy.academy.JavaClient.controlles;

import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.services.SearchService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/index")
public class IndexController {
@Autowired
    private final SearchService searchService;

    public IndexController(SearchService searchService) {
        this.searchService = searchService;
    }


    @PutMapping("/create")
    public ResponseEntity<String> createIndex() {
        try {
            searchService.createIndex();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error creating index");
        }

        return ResponseEntity.ok("Index created");
    }

    /**
     * POST /index - Indexes a new document
     *
     * @param movie - movie to be indexed
     * @return ResponseEntity - 200 if the document was indexed and 500 if there was an error
     */

    @PostMapping("/{indexName}")
    public ResponseEntity<Movie> indexDocument(@RequestBody Movie movie) {
        try {
            searchService.indexDocument(movie);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.created(null).body(movie);
    }

    /**
     * POST /index/imdb - Indexes imdb data in the index
     *
     * @param basicsFile  - Title basics file containing the data to be indexed
     * @param ratingsFile - Ratings file containing the data to be indexed
     * @oaram akasFile - Akas file containing the data to be indexed
     */
    @PostMapping("/imdb")

    public ResponseEntity indexImdbData(@RequestParam("basics") MultipartFile basicsFile,
                                        @RequestParam("ratings") MultipartFile ratingsFile,
                                        @RequestParam("akas") MultipartFile akasFile,
                                        @RequestParam("crew") MultipartFile crewFile) {
        try {
            searchService
                    .indexImdbData(basicsFile, ratingsFile, akasFile, crewFile);
        } catch (BulkIndexException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.accepted().build();
    }

}