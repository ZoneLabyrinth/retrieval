package com.retrieval.models;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by sks on 2017/10/12.
 */
public class InnovativeMappingModel {
    @Field
    private String Description;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "[description=" + Description + "]";
    }

}
