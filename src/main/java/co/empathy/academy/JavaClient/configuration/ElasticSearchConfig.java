package co.empathy.academy.JavaClient.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {


    @Bean
    public ElasticsearchClient getElasticSearchClient() {
        // Create the low-level client
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200),
                new HttpHost("elasticsearch", 9200)).build();
        // Create the HLRC
        RestHighLevelClient hlrc = new RestHighLevelClientBuilder(restClient)
                .setApiCompatibilityMode(true)
                .build();

        // Create the Java API Client with the same low level client
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
        // hlrc and esClient share the same httpClient

    }
}











