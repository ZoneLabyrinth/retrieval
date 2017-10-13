package com.retrieval.models;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Created by sks on 2017/9/21.
 */
public class PublicOpionModel {
    @Field
    private String id;
    @Field
    private String title;
    @Field
    private String url;
    @Field
    private String dateTime;
    @Field
    private String content;


    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title= title;
    }
    public String getUrl(){
        return url;
    }



    public void setUrl(String url){
        this.url =url;
    }

    public String getDateTime(){
        return dateTime;
    }

    public void setDateTime(String dateTime){
        this.dateTime = dateTime;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }


    @Override
    public String toString() {
        return "[id"+id+"title:"+title+",url:"+url+",dateTime:"+dateTime+"]";
    }
}
