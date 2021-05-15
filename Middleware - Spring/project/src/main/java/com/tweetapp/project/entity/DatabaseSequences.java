package com.tweetapp.project.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DatabaseSequences")
public class DatabaseSequences {

	@Id
    private String id;

    private long seq;

    public DatabaseSequences() {}

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return this.seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
    
}
