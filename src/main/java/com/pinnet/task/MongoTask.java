package com.pinnet.task;

import com.pinnet.domain.User;
import com.pinnet.service.IMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * 对象存储，测试
 */
@Component
public class MongoTask implements CommandLineRunner {

    @Autowired
    private IMongoService mongoService;

    @Override
    public void run(String... args) throws Exception {
        mongoService.save(new User(1L, "test1", 25));
        mongoService.save(new User(2L, "test2", 25));
        System.out.println(mongoService.find(Query.query(Criteria.where("name").regex("test")), User.class));
        System.out.println(mongoService.findOne(Query.query(Criteria.where("name").is("test2")), User.class));
        mongoService.delete(Query.query(Criteria.where("name").is("test2")), User.class);
    }
}
