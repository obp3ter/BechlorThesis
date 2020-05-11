package com.obpeter.thesis.learn.client;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.SneakyThrows;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ESAccess {

    @Autowired
    RestHighLevelClient client;

    @SneakyThrows
    public long count(String index, QueryBuilder queryBuilder) {
        CountRequest countRequest = new CountRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        countRequest.source(searchSourceBuilder);
        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
        return countResponse.getCount();
    }
    @SneakyThrows
    public List<String> getField(String index, QueryBuilder queryBuilder,String field){
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.fetchSource(new String[]{field},null);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        return StreamSupport.stream(search.getHits().spliterator(),false)
                .map(SearchHit::getSourceAsString)
                .map(this::formatJson)
                .collect(Collectors.toList());
    }
    @SneakyThrows
    public String getPosInSorted(String index,QueryBuilder queryBuilder, String field,int idx,SortOrder sortOrder){
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.fetchSource(new String[]{field},null);
        searchSourceBuilder.from(idx);
        searchSourceBuilder.size(1);
        searchSourceBuilder.sort(field, sortOrder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        return StreamSupport.stream(search.getHits().spliterator(),false)
                .map(SearchHit::getSourceAsString)
                .map(this::formatJson)
                .findFirst().get();
    }

    String formatJson(String json){
        return (json.contains("\":\"")?"\""+(json.split("\":\"")[1]):json.split(":")[1]).split("}")[0];
    }

}
