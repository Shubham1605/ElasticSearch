package com.securenow.hospitallocator.service;

import com.securenow.hospitallocator.model.Hospital;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.securenow.hospitallocator.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class LocatorService {

    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public LocatorService(@Qualifier("client_rest") RestHighLevelClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }



    public List<Hospital> findAll() throws IOException {


        SearchRequest searchRequest = buildSearchRequest(Constant.INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(1000);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);
    }


    public List<Hospital> findByProvider(String name) throws IOException {


        SearchRequest searchRequest = buildSearchRequest(Constant.INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder matchQueryBuilder = QueryBuilders
                .matchQuery("provider_Name",name)
                .operator(Operator.AND);

        searchSourceBuilder.query(matchQueryBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);

    }

    public List<Hospital> findByFuzzyProvider(String name) throws IOException{


        SearchRequest searchRequest = buildSearchRequest(Constant.INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("provider_Name",name)
                .fuzziness(Fuzziness.AUTO);


        searchSourceBuilder.query(matchQueryBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);

    }

    public List<Hospital> multiMatch(String name) throws IOException{


        SearchRequest searchRequest = buildSearchRequest(Constant.INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(100);


        QueryBuilder matchQueryBuilder= QueryBuilders.multiMatchQuery(name).field("hospitalName",4.0f).field("hospitalAddess").field("stateName").field("cityName").field("pinCode")
                .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS).operator(Operator.OR);

       searchSourceBuilder.query(matchQueryBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);

    }

    public List<Hospital> searchSelectionBased(String name,String city,String source) throws IOException{


        SearchRequest searchRequest = buildSearchRequest(Constant.INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(100);
        QueryBuilder matchQueryBuilder;
        QueryBuilder queryBuilderCity =  null;
        QueryBuilder queryBuilderSource = null;
        BoolQueryBuilder boolQueryBuilder;

        if(city.equals("") && source.equals("")){

            matchQueryBuilder=QueryBuilders.multiMatchQuery(name).field("hospitalName",3.0f).field("hospitalAddess").field("stateName").field("cityName").field("pinCode").field("source",3.0f)
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS).operator(Operator.OR);
            boolQueryBuilder= new BoolQueryBuilder().should(matchQueryBuilder);
        }
        else if(!city.equals("") && source.equals("")){
            matchQueryBuilder=QueryBuilders.multiMatchQuery(name).field("hospitalName",3.0f).field("hospitalAddess").field("stateName").field("pinCode").field("source",3.0f)
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS).operator(Operator.OR);

            queryBuilderCity=QueryBuilders.matchQuery("cityName",city);

            boolQueryBuilder= new BoolQueryBuilder().must(queryBuilderCity).should(matchQueryBuilder);
        }
        else if(city.equals("")){
            matchQueryBuilder=QueryBuilders.multiMatchQuery(name).field("hospitalName",3.0f).field("hospitalAddess").field("cityName").field("stateName").field("pinCode")
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS).operator(Operator.OR);

            queryBuilderSource=QueryBuilders.matchQuery("source",source);

            boolQueryBuilder= new BoolQueryBuilder().must(queryBuilderSource).should(matchQueryBuilder);
        }
        else{
            matchQueryBuilder=QueryBuilders.multiMatchQuery(name).field("hospitalName",3.0f).field("hospitalAddess").field("stateName").field("pinCode")
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS).operator(Operator.OR);

            queryBuilderSource = QueryBuilders.matchQuery("source",source);
            queryBuilderCity = QueryBuilders.matchQuery("cityName",city);

            boolQueryBuilder= new BoolQueryBuilder().must(queryBuilderCity).must(queryBuilderSource).should(matchQueryBuilder);
        }




        searchSourceBuilder.query(boolQueryBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);

    }




    public List<Hospital> searchAsYouType(String name) throws IOException {

        SearchRequest searchRequest = buildSearchRequest(Constant.INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

       QueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(name,"provider_Name","provider_Name._2gram","provider_Name._3gram","address","address._2gram","address._3gram","city_Name","city_Name._2gram","city_Name._3gram","state_Name","state_Name._2gram","state_Name._3gram");

        searchSourceBuilder.query(matchQueryBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);
    }


    private List<Hospital> getSearchResult(SearchResponse response) {

        SearchHit[] searchHit = response.getHits().getHits();

        List<Hospital> hospitals = new ArrayList<>();
        Hospital temp;

        for (SearchHit hit : searchHit){

            temp=objectMapper
                    .convertValue(hit
                            .getSourceAsMap(), Hospital.class);
            temp.setScore(hit.getScore());
            hospitals.add(temp);

        }
        Collections.sort(hospitals,Collections.reverseOrder());
        return hospitals;
    }

    private SearchRequest buildSearchRequest(String index) {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        return searchRequest;
    }
}
