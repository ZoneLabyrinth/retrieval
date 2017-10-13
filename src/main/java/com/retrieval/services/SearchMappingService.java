package com.retrieval.services;

import com.retrieval.models.SearchMappingModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.util.Hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sks on 2017/10/10.
 */
public class SearchMappingService {
    private HttpSolrClient solrServer;

    public SearchMappingService(HttpSolrClient solrServer) {
        this.solrServer = solrServer;
    }

    public HashMap searching(String keywords) throws Exception {
        HashMap mapping = new HashMap();

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("keywords:" + keywords);
        solrQuery.setRows(6);
        solrQuery.setSort("publish_time", SolrQuery.ORDER.desc);
//
//        QueryResponse res = solrServer.query(solrQuery);
//
//        List Data = new ArrayList();


        boolean isHighlighting = !StringUtils.equals("*", keywords) && StringUtils.isNotEmpty(keywords);
//
        if (isHighlighting) {
            // 设置高亮
            solrQuery.setHighlight(true); // 开启高亮组件
            solrQuery.addHighlightField("keywords");// 高亮字段
            solrQuery.setHighlightSimplePre("<em>");// 标记，高亮关键字前缀
            solrQuery.setHighlightSimplePost("</em>");// 后缀
        }
        //执行查询
        QueryResponse res = this.solrServer.query(solrQuery);
//        System.out.println("48"+res.getBeans(SearchMappingModel.class));
        List<SearchMappingModel> models = res.getBeans(SearchMappingModel.class);

        if (isHighlighting) {
            // 将高亮的标题数据写回到数据对象中
            Map<String, Map<String, List<String>>> map = res.getHighlighting();
            for (Map.Entry<String, Map<String, List<String>>> highlighting : map.entrySet()) {

                for (SearchMappingModel model : models) {
                    /**
                     * 若查询无匹配，则抛出异常，返回空
                     */
                    try {
                        if (!highlighting.getKey().equals(model.getKeywords().toString())) {
                            continue;
                        }
                        model.setKeywords(StringUtils.join(highlighting.getValue().get("keywords"), ""));
                    }catch (Exception e){
                        System.out.println(e);
                        model.setKeywords("无结果");
                        List<SearchMappingModel> noResult = new ArrayList();
                        noResult.add(model);
                        mapping.put("data", noResult);
                        return mapping;
                    }





//                    if (model.getKeywords() != null) {
//                        if (!highlighting.getKey().equals(model.getKeywords().toString())) {
//                            continue;
//                        }
//                        model.setKeywords(StringUtils.join(highlighting.getValue().get("keywords"), ""));
//                    } else {
//                        model.setKeywords("无结果");
//                        List<SearchMappingModel> noResult = new ArrayList();
//                        noResult.add(model);
//                        mapping.put("data", noResult);
//                        return mapping;
//                    }
//                    String url = highlighting.getValue().get("url").toString();
//                    url = url.replaceAll("[\\[\\]]","");
//                    model.setUrl(url);

//                    model.setUrl(StringUtils.join(highlighting.getValue().get("url"), ""));
                    break;
                }
            }
        }
        mapping.put("data", models);

        return mapping;
    }


}
