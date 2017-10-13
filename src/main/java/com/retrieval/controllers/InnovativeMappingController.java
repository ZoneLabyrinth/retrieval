package com.retrieval.controllers;

import com.retrieval.services.InnovativeMappingService;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by sks on 2017/10/12.
 */
@RestController
public class InnovativeMappingController {
    private InnovativeMappingService solrServer;


    @RequestMapping(value = "/innovativeMapping",method = RequestMethod.GET)
    public ResponseEntity<Object> innovativeMapping (@RequestParam(value = "description",defaultValue ="1")String description) throws Exception{
        System.out.println("====================== 创新平台");

        String serverUrl = "http://10.10.4.50:8983/solr/Innovation_Platform_shard1_replica3";

        HttpSolrClient solrClient = new HttpSolrClient(serverUrl);

        solrServer = new InnovativeMappingService(solrClient);


        HashMap res = solrServer.searching(description);

        Object data  = new HashMap();

        data = res;

        return new ResponseEntity<Object>(data, HttpStatus.CREATED);

    }




}
