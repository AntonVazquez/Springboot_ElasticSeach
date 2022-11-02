package co.empathy.academy.JavaClient.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.empathy.academy.JavaClient.services.SearchEngine;
import co.empathy.academy.JavaClient.services.SearchEngineImpl;
import co.empathy.academy.JavaClient.services.SearchService;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {


    @Bean
    public ElasticsearchClient getElasticSearchClient() {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
    }

    @Bean
    public RestClient getRestClient() {
        return RestClient.builder(new HttpHost("localhost", 9200)).build();
    }

    @Bean
    public SearchEngine searchEngine() {
        return new SearchEngineImpl();
    }

    @Bean
    public SearchService searchService(SearchEngine searchEngine) {
        return new SearchService(searchEngine);
    }

}
