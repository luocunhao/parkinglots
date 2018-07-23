package cn.xlink.parkinglots.server.mongo_native.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import lombok.Getter;
import lombok.Setter;

@Component
@Configuration
@ConfigurationProperties(prefix = "mongo")
@Setter
@Getter
public class MongoConfiguration {

	private String hosts;

	private int connectionsPerHost;

	private int threadsAllowedToBlockForConnectionMultiplier;

	private int connectTimeout;

	private int maxWaitTime;

	private boolean autoConnectRetry;

	private boolean socketKeepAlive;

	private int socketTimeout;

	private String username;

	private String password;

	private String replicaSet;

	private String database;

	@Bean
	public MongoClient mongoClient() {
		MongoClientOptions.Builder optBuilder = MongoClientOptions.builder();
		optBuilder.connectionsPerHost(this.connectionsPerHost);
		optBuilder.threadsAllowedToBlockForConnectionMultiplier(this.threadsAllowedToBlockForConnectionMultiplier);
		optBuilder.connectTimeout(this.connectTimeout);
		optBuilder.maxWaitTime(this.maxWaitTime);
		optBuilder.socketKeepAlive(this.socketKeepAlive);
		optBuilder.socketTimeout(this.socketTimeout);
		optBuilder.sslEnabled(false);
		optBuilder.sslInvalidHostNameAllowed(false);


		if (StringUtils.isEmpty(this.replicaSet) == false) {
			optBuilder.requiredReplicaSetName(this.replicaSet);
		}

		List<ServerAddress> seeds = new ArrayList<>();
		List<MongoCredential> credentials = Arrays.asList(MongoCredential.createScramSha1Credential(this.username, "admin", this.password
				.toCharArray()));
		for (String hostString : this.hosts.split(",")) {
			String host = hostString.split(":")[0];
			int    port = Integer.valueOf(hostString.split(":")[1]);
			seeds.add(new ServerAddress(host, port));
		}
		return new MongoClient(seeds, credentials, optBuilder.build());
//		return  new MongoClient("118.89.35.253",27017);
	}

	@Bean
	public MongoDatabase mongoDatabase() {
		System.out.println(mongoClient().getDatabase(this.database));
		return mongoClient().getDatabase(this.database);
	}
	@Bean
	public  MongoTemplate mongoTemplate() throws Exception {  
		
//	      return new MongoTemplate(mongoClient(), this.database);  
		/*
		MappingMongoConverter converter = 
				new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());
			converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		 
			MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), converter);
		 
			return mongoTemplate;
			*/
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());

        // Remove _class
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return new MongoTemplate(mongoDbFactory(), converter);

	  } 
	@Bean
	public  MongoDbFactory mongoDbFactory() throws Exception {  
	    return new SimpleMongoDbFactory(mongoClient(), this.database);  
	  } 

}
