package com.retrieval.controllers;

import com.retrieval.models.PublicOpionModel;
import com.retrieval.services.PublicOpionService;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sks on 2017/9/21.
 */
@RestController
public class PublicOpionController {

    private PublicOpionService solrServer;

    private HttpSolrClient httpSolrServer;


    @RequestMapping(value = "/searching",method = RequestMethod.POST)
    @ResponseBody
    public List<PublicOpionModel> searching(@RequestBody(required = false)String navtitle)throws Exception{
        System.out.println("====================================");

        System.out.println();

        String serverUrl ="http://10.10.4.48:8983/solr/uradar_article_shard2_replica2";

        HttpSolrClient httpSolrServer = new HttpSolrClient(serverUrl);

        solrServer = new PublicOpionService(httpSolrServer);

        List<PublicOpionModel> models = solrServer.search(navtitle,10);

        return models;
    }

}
