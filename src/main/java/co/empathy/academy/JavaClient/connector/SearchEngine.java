package co.empathy.academy.JavaClient.connector;


import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.empathy.academy.JavaClient.configuration.ElasticSearchConfig;
import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.services.QueryService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class SearchEngine {



    @Autowired
    private ElasticSearchConfig elasticSearchConfig;
    @Autowired
    private QueryService queries;


    public void createIndex() throws IOException {
        try {
            elasticSearchConfig.getElasticSearchClient().indices().delete(d -> d.index("imdb"));
        } catch (Exception e) {
            // Ignore
        }
        elasticSearchConfig.getElasticSearchClient().indices().create(c -> c.index("imdb"));
    }

    public void putSettings() throws IOException {
        elasticSearchConfig.getElasticSearchClient().indices().close(c -> c.index("imdb"));

        InputStream analyzer = getClass().getClassLoader().getResourceAsStream("custom_analyzer.json");
        elasticSearchConfig.getElasticSearchClient().indices().putSettings(p -> p.index("imdb").withJson(analyzer));

        elasticSearchConfig.getElasticSearchClient().indices().open(o -> o.index("imdb"));
    }

    public void putMapping() throws IOException {
        InputStream mapping = getClass().getClassLoader().getResourceAsStream("mapping.json");
        elasticSearchConfig.getElasticSearchClient().indices().putMapping(p -> p.index("imdb").withJson(mapping));
    }



    public void indexDocument(Movie movie) throws IOException {
        elasticSearchConfig.getElasticSearchClient().index(i -> i
                .index("imdb")
                .id(movie.getTconst())
                .document(movie));
    }

    public void indexBulk(List<Movie> movies) throws IOException, BulkIndexException {
        BulkRequest.Builder request = new BulkRequest.Builder();

        movies.forEach(movie -> request.operations(op -> op
                .index(i -> i
                        .index("imdb")
                        .id(movie.getTconst())
                        .document(movie))));

        BulkResponse bulkResponse = elasticSearchConfig.getElasticSearchClient().bulk(request.build());
        if (bulkResponse.errors()) {
            throw new BulkIndexException("Error indexing bulk");
        }
    }





    public int simpleSearch(String query) {
        if (query == null) {
            throw new RuntimeException("Query is mandatory");
        }
        return query.length();
    }

    /**
     * Echoes the query with current ElasticSearch version
     * @param query : given query
     * @return : SearchResponseCustom with the info
     * @throws IOException : if can't connect with ElasticServer
     */

    /*
    public Response searchQuery(String query) throws IOException {
        return new Response(query);
    }
    */


    /**
     * Makes a query to a specific index
     * @param indexName : index where the search is going to take place
     * @param body : settings of the query
     * @return : SimpleResponse
     */
    public List<Movie> searchIndex(String indexName, String body) throws JSONException {
        // First checks parameters
        if(indexName == null)
            throw new IllegalArgumentException("ERROR: missing required parameter <indexName>");
        else if (body == null)
            throw new IllegalArgumentException("ERROR: missing JSON body in PUT request.");


        JSONObject query = new JSONObject(body).getJSONObject("query");
        Iterator<String> keys = query.keys();
        String queryType = (String) query.keys().next();
        JSONObject typeJSON = query.getJSONObject(queryType);


         if(queryType.equals("term"))
            return queries.makeTermQuery(indexName, typeJSON);
        else if(queryType.contains("terms"))
            return queries.makeTermsQuery(indexName, typeJSON);
        else
            throw new IllegalArgumentException("ERROR: query search not allowed");
    }

    /**
     * Custom search that filters with given parameters
     *
     * @param genre      : genre(s) of the movie
     * @param maxYear    : upper range of the year interval
     * @param minYear    : lower range of the year interval
     * @param maxMinutes : upper range of the runtimeMinutes interval
     * @param minMinutes : lower range of the runtimeMinutes interval
     * @param maxScore   : upper range of the rate interval
     * @param minScore   : lower range of the rate interval
     * @param maxNHits
     * @param type       : if it's a movie, short or tv-series
     * @return : List of movies that satisfy the custom search
     */

    public List<Movie> search(Optional<String> genre, Optional<Integer> maxYear, Optional<Integer> minYear,
                              Optional<Integer> maxMinutes, Optional<Integer> minMinutes,
                              Optional<Double> maxScore, Optional<Double> minScore, Optional<Integer> maxNHits, Optional<String> type) {

        List<Query> allQueries = new ArrayList<>();

        // Must be a movie or Tv-Series. Default value = "movie"
        if(type.isPresent())
            allQueries.add(queries.makeTermQueryMust("titleType", String.valueOf(type.get())));
        else
            allQueries.add(queries.makeTermQueryMust("titleType", "movie"));

        // It can't be an adult movie
        allQueries.add(queries.makeTermQueryMust("isAdult", "false"));

        // Should have one of these genres (or must?)
        if(genre.isPresent()){
            String[] genres = genre.get().split(",");
            allQueries.add(queries.makeTermsQueryShould("genres", genres));
        }

        // Must be in this year range
        if(maxYear.isPresent() && minYear.isPresent()){
            // It's for movies, so we use start year
            System.out.println(maxYear.get());
            System.out.println(minYear.get());
            allQueries.add(queries.makeRangeQuery("startYear",
                    maxYear.get(), minYear.get()));
        }

        // Must be in this runTime range
        if(maxMinutes.isPresent() && minMinutes.isPresent())
            allQueries.add(queries.makeRangeQuery("runtimeMinutes",
                    maxMinutes.get(),minMinutes.get()));

        // Must be in this ratingScore range
        if(maxScore.isPresent() && minScore.isPresent())
            allQueries.add(queries.makeRangeQuery("averageRating",
                    maxScore.get(),minScore.get()));

        // Make the final query with all the previous filters
        co.elastic.clients.elasticsearch._types.query_dsl.Query finalQuery = queries.filterAllQueries(allQueries);

        // Perform this custom query
        return queries.performQuery(finalQuery);
    }



}




