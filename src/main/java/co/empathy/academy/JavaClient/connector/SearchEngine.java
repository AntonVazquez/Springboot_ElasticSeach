package co.empathy.academy.JavaClient.connector;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;

import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.exception.RecordNotFoundException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.utils.IMDbReader;
import co.empathy.academy.JavaClient.utils.QueryBuilderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchEngine {



    private final ElasticsearchClient elasticsearchClient;

    public SearchEngine(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }




    public void createIndex() throws IOException {
        try {
            elasticsearchClient.indices().delete(d -> d.index("imdb"));
        } catch (Exception e) {
            // Ignore
        }
        elasticsearchClient.indices().create(c -> c.index("imdb"));
    }

    public void putSettings() throws IOException {
        elasticsearchClient.indices().close(c -> c.index("imdb"));

        InputStream analyzer = getClass().getClassLoader().getResourceAsStream("custom_analyzer.json");
        elasticsearchClient.indices().putSettings(p -> p.index("imdb").withJson(analyzer));

        elasticsearchClient.indices().open(o -> o.index("imdb"));
    }

    public void putMapping() throws IOException {
        InputStream mapping = getClass().getClassLoader().getResourceAsStream("mapping.json");
        elasticsearchClient.indices().putMapping(p -> p.index("imdb").withJson(mapping));
    }



    public void indexDocument(Movie movie) throws IOException {
        elasticsearchClient.index(i -> i
                .index("imdb")
                .id(movie.getTconst())
                .document(movie));
    }

}