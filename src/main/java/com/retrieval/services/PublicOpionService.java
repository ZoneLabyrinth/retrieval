package com.retrieval.services;

import com.retrieval.models.PublicOpionModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrDocument;

import java.util.ArrayList;
import java.util.List;

import static org.apache.solr.client.solrj.SolrQuery.ORDER.desc;

/**
 * Created by sks on 2017/9/21.
 */
public class PublicOpionService {
    private HttpSolrClient solrServer;

    public PublicOpionService (HttpSolrClient solrServer){
        this.solrServer = solrServer;
    }

    /**
     * 创建搜索方法
     * @param keywords
     * @param rows
     * @return List
     */

    public List<PublicOpionModel> search(String keywords,Integer rows) throws Exception{
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("keywords:"+keywords);
        solrQuery.setRows(rows);
        solrQuery.addSort("publish_time",desc);

        QueryResponse res = solrServer.query(solrQuery);

        List data = new ArrayList();

        SolrDocumentList docs = res.getResults();


        for (SolrDocument doc : docs){
            PublicOpionModel models = new PublicOpionModel();
            String title = doc.getFieldValue("title").toString();
            String url = doc.getFieldValue("url").toString();
            String dateTime = doc.getFieldValue("publish_time").toString();
            url = url.replaceAll("[\\[\\]]","");

            dateTime = dateTime.replaceAll("[\\[\\]]","");
            models.setUrl(url);
            models.setTitle(title);
            models.setDateTime(dateTime);
            data.add(models);

        }
        System.out.println(data);

        /**
         * 以下方法可以得到查询直接返回的title和url，但url原类型为array；
         */
//        boolean isHighlighting = !StringUtils.equals("*", keywords) && StringUtils.isNotEmpty(keywords);
//
//        if (isHighlighting) {
//            // 设置高亮
//            solrQuery.setHighlight(true); // 开启高亮组件
//            solrQuery.addHighlightField("title");// 高亮字段
//            solrQuery.addHighlightField("url");
//            solrQuery.setHighlightSimplePre("<em>");// 标记，高亮关键字前缀
//            solrQuery.setHighlightSimplePost("</em>");// 后缀
//        }
//        //执行查询
//        QueryResponse res = this.solrServer.query(solrQuery);
//        System.out.println( res.getBeans(PublicOpionModel.class));
//        List<PublicOpionModel> models = res.getBeans(PublicOpionModel.class);
//
//        if (isHighlighting) {
//            // 将高亮的标题数据写回到数据对象中
//            Map<String, Map<String, List<String>>> map = res.getHighlighting();
//            for (Map.Entry<String, Map<String, List<String>>> highlighting : map.entrySet()) {
//                for (PublicOpionModel model : models) {
//                    if (!highlighting.getKey().equals(model.getTitle().toString())) {
//                        continue;
//                    }
//                    model.setTitle(StringUtils.join(highlighting.getValue().get("title"), ""));
//
////                    String url = highlighting.getValue().get("url").toString();
////                    url = url.replaceAll("[\\[\\]]","");
////                    model.setUrl(url);
//
////                    model.setUrl(StringUtils.join(highlighting.getValue().get("url"), ""));
//                    break;
//                }
//            }
//        }


        return data;

    }


}
