package co.empathy.academy.JavaClient.services;



import co.empathy.academy.JavaClient.model.MyResponse;

import org.elasticsearch.client.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class SearchEngineImpl implements SearchEngine {

    // This method should make a request to Elasticsearch to retrieve search results
    // For our example we'll just return the query length as number of results
    @Autowired
    public ElasticsearchClient elasticsearchClient;

    @Override
    public int search(String query) {
        if (query == null) {
            throw new RuntimeException("Query is mandatory");
        }
        return query.length();
    }

    @Override
    public MyResponse searchQuery(String query) throws IOException {
        String numberVersion = elasticsearchClient.getElasticVersion();
        return new MyResponse(query, numberVersion);
    }
}






}


