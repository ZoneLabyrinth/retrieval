package com.retrieval.controllers;

import com.retrieval.services.SearchMappingService;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sks on 2017/10/10.
 */
@RestController
public class SearchMappingController {
    private SearchMappingService solrServer;
    private HttpSolrClient httpSolrClient;

        @RequestMapping(value = "/searchMapping", method = RequestMethod.GET)
    public ResponseEntity<Object> searchingMapping(@RequestParam(value = "keywords") String keywords) throws Exception {
        System.out.println("==========================匹配"+keywords);

        String solrUrl = "http://10.10.4.48:8983/solr/uradar_article_shard2_replica2";


        HttpSolrClient httpSolrClient = new HttpSolrClient(solrUrl);

        solrServer = new SearchMappingService(httpSolrClient);

//        Object data = solrServer.searching(keywords);

        HashMap res = solrServer.searching(keywords);


        Object data = new HashMap();

        data = res;


        return new ResponseEntity<Object>(data, HttpStatus.CREATED);


    }


}
