package com.gevorg.task.adminmax.config;

import com.github.mongobee.Mongobee;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
@PropertySource("classpath:mongo.properties")
public class MongoConfig extends AbstractMongoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(MongoConfig.class);

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @Value("${spring.data.mongodb.host}")
    private String dbHost;

    @Value("${spring.data.mongodb.port}")
    private int dbPort;


    @Override
    public String getDatabaseName() {
        return dbName;
    }

    @Override
    @Bean
    public Mongo mongo() {
        return mongoClient();
    }

    @Bean
    public MongoClient mongoClient(){
        MongoClient mongoClient = new MongoClient(new ServerAddress(dbHost, dbPort));
        return mongoClient;
    }

    @Bean
    public Mongobee mongobee(Environment environment){
        Mongobee runner = new Mongobee(mongoClient());
        runner.setDbName(dbName);         // host must be set if not set in URI
        runner.setChangeLogsScanPackage("com.gevorg.task.adminmax.migrations"); // the package to be scanned for changesets
        runner.setSpringEnvironment(environment);
        return runner;
    }

    @Bean
    public MongoDbFactory mongoDbFactory(){

        return new SimpleMongoDbFactory(mongoClient(),dbName);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        // Don't save _class to mongo
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(),mappingMongoConverter);
        return mongoTemplate;
    }
}
