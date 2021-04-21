package net.steampunkfoundry.techdemo.docs.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import net.steampunkfoundry.techdemo.docs.domain.DatabaseSequence;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormNumberGenerator {

    public static final String FORM_TYPE = "I131";
    private final MongoOperations mongoOperations;

    public String generateSequence(String seqName) {

        int year = LocalDateTime.now().getYear();

        String prefix = year + "_" + FORM_TYPE + "_";

        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return prefix + counter.getSeq();
    }

}
