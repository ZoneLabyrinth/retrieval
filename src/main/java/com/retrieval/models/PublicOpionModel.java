package com.retrieval.models;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Created by sks on 2017/9/21.
 */
public class PublicOpionModel {
    @Field
    private String title;
    @Field
    private String url;
    @Field
    private String dateTime;

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


    @Override
    public String toString() {
        return "[title:"+title+",url:"+url+"dateTime:"+dateTime+"]";
    }
}
