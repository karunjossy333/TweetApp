package com.tweetapp.project.service;

//import java.util.List;
//import java.util.Objects;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.FindAndModifyOptions;
//import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

//import com.tweetapp.project.entity.DatabaseSequences;

@Service
public class SequenceGeneratorService {

//	private MongoOperations mongoOperations;
//
//    @Autowired
//    public SequenceGeneratorService(MongoOperations mongoOperations) {
//        this.mongoOperations = mongoOperations;
//    }
//
//    public long generateSequence(String seqName) {
//    	Query query = new Query();
//    	query.addCriteria(Criteria.where("_id").is(seqName));
//        DatabaseSequences counter = mongoOperations.findAndModify(query,
//                new Update().inc("seq",1), new FindAndModifyOptions().returnNew(true).upsert(true),
//                DatabaseSequences.class);
//        return !Objects.isNull(counter) ? counter.getSeq() : 1;
//    }
//    
//    public void decrement(String seqName) {
//    	Query query = new Query();
//    	query.addCriteria(Criteria.where("_id").is(seqName));
//    	List<DatabaseSequences> isUpdatabale = mongoOperations.find(query, DatabaseSequences.class);
//    	DatabaseSequences data = isUpdatabale.get(0);
//    	if(Objects.isNull(isUpdatabale)) {
//    		return;
//    	} else if(isUpdatabale.get(0) != null && data.getSeq() > 0) {
//    		mongoOperations.findAndModify(query,
//                new Update().inc("seq",-1), new FindAndModifyOptions().returnNew(true).upsert(true),
//                DatabaseSequences.class);
//        	return;
//    	}
//    }
    
}
