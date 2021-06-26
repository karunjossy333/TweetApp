package com.tweetapp.project.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.tweetapp.project.entity.UserDetails;
import com.tweetapp.project.service.SequenceGeneratorService;

@Component
public class UserModelListener extends AbstractMongoEventListener<UserDetails> {

	private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public UserModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<UserDetails> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(UserDetails.SEQUENCE_NAME));
        }
    }
    
}
