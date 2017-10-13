package com.retrieval.services;

import com.retrieval.models.PublicOpionModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrDocument;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static org.apache.solr.client.solrj.SolrQuery.ORDER.desc;

/**
 * Created by sks on 2017/9/21.
 */
public class PublicOpionService {
    private HttpSolrClient solrServer;


    public PublicOpionService(HttpSolrClient solrServer) {
        this.solrServer = solrServer;
    }

    /**
     * 创建搜索方法
     *
     * @param keywords
     * @param rows
     * @return List
     */

//    public List<PublicOpionModel> search(String keywords,Integer page, Integer rows) throws Exception{
    public HashMap search(String keywords, Integer page, Integer rows) throws Exception {

        HashMap map = new HashMap();

        /**
         * solr查询
         */

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("keywords:" + keywords);
        solrQuery.setRows(rows);
        solrQuery.setStart((Math.max(page, 1) - 1) * rows);
        solrQuery.addSort("publish_time", desc);
        System.out.println("+++++++++++++++++++++++" + solrServer.query(solrQuery).getResults().getNumFound());
        QueryResponse res = solrServer.query(solrQuery);

        List data = new ArrayList();

        SolrDocumentList docs = res.getResults();

        /**
         * 将取得的结果按需要添加入data里；
         */

        for (SolrDocument doc : docs) {
            PublicOpionModel models = new PublicOpionModel();
            String id = doc.getFieldValue("id").toString();
            String title = doc.getFieldValue("title").toString();
            String url = doc.getFieldValue("url").toString();

            //改变文本颜色
            String contents = doc.getFieldValue("content").toString();
//            String regx =

            Pattern pattern = Pattern.compile("<([^>]*)>");

            contents = contents.replaceAll("<([^>]*)>","");

            contents = contents.replaceAll(keywords,"<em>"+keywords+"</em>");

            title = title.replaceAll(keywords,"<em>"+keywords+"</em>");

            /**
             * 此处由于取得时间的变为CTS引发错时区错误，在原来时间基础偏移了8小时，
             * 通过将时区转换获得正确时间
             */
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatStr = formatter.format(doc.getFieldValue("publish_time"));
//            System.out.println(formatStr);
            Date time = formatter.parse(formatStr);
            TimeZone timeZone = TimeZone.getTimeZone("GMT-8:00");
// dateTime是格林威治时间
            long chineseMills = time.getTime() + timeZone.getRawOffset();
            Date chineseDateTime = new Date(chineseMills);

            String dateTime = formatter.format(chineseMills);
//            System.out.println(dateTime);
            url = url.replaceAll("[\\[\\]]", "");

            dateTime = dateTime.replaceAll("[\\[\\]]", "");
            models.setId(id);
            models.setUrl(url);
            models.setTitle(title);
            models.setDateTime(dateTime);
            models.setContent(contents);
            data.add(models);

        }

        map.put("data", data);
        map.put("X-Total-Count", docs.getNumFound());


        return map;

    }


}
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



