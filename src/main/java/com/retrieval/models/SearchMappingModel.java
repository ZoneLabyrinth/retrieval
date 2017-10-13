package com.retrieval.models;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by sks on 2017/10/10.
 */
public class SearchMappingModel {
    @Field
    private String keywords;

    public String getKeywords(){
        return keywords;
    }

    public void setKeywords(String keywords){
        this.keywords = keywords;
    }

    @Override
    public String toString(){
        return "[keywords"+keywords+"]";
    }

}
