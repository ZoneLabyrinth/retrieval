package com.retrieval.services;

import com.retrieval.models.InnovativeMappingModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sks on 2017/10/12.
 */
public class InnovativeMappingService {
    private HttpSolrClient solrClient;

    public InnovativeMappingService(HttpSolrClient solrClient) {
        this.solrClient = solrClient;
    }

    ;

    public HashMap searching(String description) throws Exception {
        HashMap mapping = new HashMap();

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("Description:" + description);
        solrQuery.setRows(6);


        boolean isHightLinghting = !StringUtils.equals("*", description) && StringUtils.isNotEmpty(description);

        if (isHightLinghting) {
            solrQuery.setHighlight(true);
            solrQuery.addHighlightField("Description");
            solrQuery.setHighlightSimplePre("<em>");// 标记，高亮关键字前缀
            solrQuery.setHighlightSimplePost("</em>");// 后缀
        }

        QueryResponse res = this.solrClient.query(solrQuery);

        List<InnovativeMappingModel> models = res.getBeans(InnovativeMappingModel.class);

        System.out.println(models);

        if (isHightLinghting) {
            Map<String, Map<String, List<String>>> map = res.getHighlighting();
            for (Map.Entry<String, Map<String, List<String>>> highlighting : map.entrySet()) {
                for (InnovativeMappingModel model : models) {
                    try{
                        if (!highlighting.getKey().equals(model.getDescription().toString())){
                            continue;
                        }
                        model.setDescription(StringUtils.join(highlighting.getValue().get("Description"), ""));

                    }catch (Exception e){
                        System.out.println(e);
                        model.setDescription("无结果");
                        List<InnovativeMappingModel> noResult = new ArrayList();
                        noResult.add(model);

                        mapping.put("data",noResult);
                        return mapping;
                    }
                    break;
                }
            }
        }
        mapping.put("data",models);

        return mapping;

    }


}
