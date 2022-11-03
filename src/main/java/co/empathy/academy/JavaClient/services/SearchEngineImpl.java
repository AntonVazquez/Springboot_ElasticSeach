package co.empathy.academy.JavaClient.services;



import co.elastic.clients.transport.Transport;
import co.empathy.academy.JavaClient.model.MyResponse;

import net.minidev.json.JSONObject;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class SearchEngineImpl implements SearchEngine {

    // This method should make a request to Elasticsearch to retrieve search results
    // For our example we'll just return the query length as number of results
    @Autowired
    public ElasticsearchClient elasticsearchClient;
    private RestClient elasticClient;

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

    @Override
    public String getElasticVersion() throws IOException {
        String version;
        try
        {
            Request request = new Request("GET", "/");
            Response response = elasticClient.performRequest(request);
            String responseStr = EntityUtils.toString(response.getEntity());
            JSONObject responseObj = new JSONObject(responseStr);
            version=responseObj.getString("version");

        } catch (IOException exception)
        {
            throw new RuntimeException("IOException in getVersion() ");
        }

        return version;
    }

}  






}


