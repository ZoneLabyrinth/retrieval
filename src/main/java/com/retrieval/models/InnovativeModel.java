package com.retrieval.models;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by sks on 2017/10/11.
 */
public class InnovativeModel {
    @Field
    private String fileName;
//    @Field
//    private String descrption;
    @Field
    private String content;

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

//    public String getDescrption(){
//    }
//    public void setDescrption(String descrption){
//        this.descrption = descrption;
//    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
//        return descrption;
        this.content = content;
    }

    @Override
    public String toString(){
        return "[data:fileName:"+fileName+",content:"+content+"]";
    }




}
