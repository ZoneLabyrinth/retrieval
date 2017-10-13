package com.retrieval.services;

import com.retrieval.models.InnovativeModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

//import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import static org.apache.solr.client.solrj.SolrQuery.ORDER.desc;

/**
 * Created by sks on 2017/10/11.
 */
public class InnovativeService {
    private HttpSolrClient solrServer;


    public InnovativeService(HttpSolrClient solrServer) {
        this.solrServer = solrServer;
    }

    /**
     * 创建搜索方法
     *
     * @param description
     * @param rows
     * @return List
     */

//    public List<PublicOpionModel> search(String keywords,Integer page, Integer rows) throws Exception{
    public HashMap search(String description, Integer page, Integer rows) throws Exception {

        HashMap map = new HashMap();

        /**
         * solr查询
         */

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("Description:" + description);
        solrQuery.setRows(rows);
        solrQuery.setStart((Math.max(page, 1) - 1) * rows);
//        solrQuery.addSort("publish_time", desc);
        System.out.println("+++++++++++++++++++++++" + solrServer.query(solrQuery).getResults().getNumFound());
        QueryResponse res = solrServer.query(solrQuery);

        List data = new ArrayList();

        SolrDocumentList docs = res.getResults();

        /**
         * 将取得的结果按需要添加入data里；
         */

        for (SolrDocument doc : docs) {
            InnovativeModel models = new InnovativeModel();
            String fileName = doc.getFieldValue("FileName").toString();
//            String description = doc.getFieldValue("description").toString();
            String content = doc.getFieldValue("Content").toString();


            /**
             * 获取内容匹配替换文字颜色
             */

            String contents = doc.getFieldValue("Content").toString();

            contents = contents.replaceAll(description,"<em>"+description+"</em>");

            fileName = fileName.replaceAll(description,"<em>"+description+"</em>");
//            Pattern pattern = Pattern.compile(description);
//            System.out.println("adf"+pattern);
//            Matcher matcher = pattern.matcher(contents);
//            int len = 0;
//            String s = "<em>" + description + "</em>";
//            System.out.println("s长度" + s.length());
//
//            while (matcher.find()) {
////                System.out.println("neirong " + contents);
//
//                String match = contents.substring(matcher.start()  + len, matcher.end() + len);
//
//
//                contents = contents.replaceFirst(match, "<em>" + description + "</em>");
//
//
//                len = len + s.length();
//            }


            /**
             * 此处由于取得时间的变为CTS引发错时区错误，在原来时间基础偏移了8小时，
             * 通过将时区转换获得正确时间
             */
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String formatStr = formatter.format(doc.getFieldValue("publish_time"));
////            System.out.println(formatStr);
//            Date time = formatter.parse(formatStr);
//            TimeZone timeZone = TimeZone.getTimeZone("GMT-8:00");
//// dateTime是格林威治时间
//            long chineseMills = time.getTime() + timeZone.getRawOffset();
//            Date chineseDateTime = new Date(chineseMills);
//
//            String dateTime = formatter.format(chineseMills);
//            System.out.println(dateTime);
//            url = url.replaceAll("[\\[\\]]", "");

//            dateTime = dateTime.replaceAll("[\\[\\]]", "");
//            models.setDescrption(description);
            models.setContent(contents);
            models.setFileName(fileName);
//            models.setDateTime(dateTime);
            data.add(models);

        }

        map.put("data", data);
        map.put("X-Total-Count", docs.getNumFound());


        return map;


    }
}