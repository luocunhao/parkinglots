package cn.xlink;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, DataSourceAutoConfiguration.class})
@MapperScan("cn.xlink.parkinglots.server")
@EnableTransactionManagement
public class ParkinglotsServer {
	public static void main(String[] args) {
		SpringApplication.run(ParkinglotsServer.class, args);
	}
}