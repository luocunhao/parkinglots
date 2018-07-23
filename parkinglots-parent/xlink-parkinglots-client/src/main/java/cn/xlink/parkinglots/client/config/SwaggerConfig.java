package cn.xlink.parkinglots.client.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerMapping;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.PropertySourcedRequestMappingHandlerMapping;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Profile({"dev", "test"})
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final List<HandlerMapping> handlerMappings;

    @Autowired(required = false)
    public SwaggerConfig(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

//    @Bean
//    public Docket docketUser() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(new ApiInfoBuilder()
//                        .title("智慧社区模块文档")
//                        .description("智慧社区模块文档")
//                        .contact(new Contact("auth", "", "auth@qq.com"))
//                        .version("0.0.1")
//                        .build())
//                .groupName("Auth")
//                .select().apis(RequestHandlerSelectors.basePackage("cn.xlink.doorlock.client.controller")).paths(PathSelectors.any())
//                .build();
//    }


/*    @Bean
    public Docket docketPayAndProduct() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("b2b后管接口用户模块文档")
                        .description("b2b后管接口用户模块文档")
                        .contact(new Contact("xx", "", "xx@qq.com"))
                        .version("0.0.1")
                        .build())
                .groupName("PayAndProduct")
                .select().apis(Predicates.or(RequestHandlerSelectors.basePackage("org.exam.web.pay"), RequestHandlerSelectors.basePackage("org.exam.web.product"))).paths(PathSelectors.any()).build();
    }
    @Bean
    public Docket docketProduct() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("b2b Web接口产品模块文档")
                        .description("b2b Web接口产品模块文档")
                        .contact(new Contact("product", "", "product@qq.com"))
                        .version("0.0.1")
                        .build())
                .groupName("商品信息")
                .select().apis(RequestHandlerSelectors.basePackage("com.berchina.b2b.modules.product.controller")).paths(PathSelectors.any())
                .build();
    }
    
    @Bean
    public Docket docketShopProduct() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("b2b Web商品分类接口模块文档")
                        .description("b2b Web商品分类接口产品模块文档")
                        .contact(new Contact("shopproduct", "", "shopproduct@qq.com"))
                        .version("0.0.1")
                        .build())
                .groupName("商品分类")
                .select().apis(RequestHandlerSelectors.basePackage("com.berchina.b2b.modules.shop.controller")).paths(PathSelectors.any())
                .build();
    }
*/
    
    @Bean
    public Docket api(){
	//添加head参数start
    	ParameterBuilder tokenPar = new ParameterBuilder();
    	List<Parameter> pars = new ArrayList<Parameter>();
    	tokenPar.name("Access-Token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	pars.add(tokenPar.build());
	//添加head参数end
 
//    	return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(new ApiInfoBuilder()
//                        .title("智慧社区模块文档")
//                        .description("智慧社区模块文档")
//                        .contact(new Contact("auth", "", "auth@qq.com"))
//                        .version("0.0.1")
//                        .build())
//                .groupName("Auth")
//                .select().apis(RequestHandlerSelectors.basePackage("cn.xlink.doorlock.client.controller")).paths(PathSelectors.any())
//                .build();
 
        return new Docket(DocumentationType.SWAGGER_2)
//            .select()
//            .apis(RequestHandlerSelectors.any())
//            .paths(PathSelectors.regex("/v2/.*"))
//            .build()
//            .globalOperationParameters(pars)
//            .apiInfo(apiInfo());
        		.select().apis(RequestHandlerSelectors.basePackage("cn.xlink.parkinglots.client.controller")).paths(PathSelectors.any())
              .build()
              .globalOperationParameters(pars)
            .apiInfo(apiInfo());
    }
 

    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//            .title("后台接口文档与测试")
//            .description("这是一个给app端人员调用server端接口的测试文档与平台")
//            .version("1.0.0")
//            .termsOfServiceUrl("http://terms-of-services.url")
//            //.license("LICENSE")
//            //.licenseUrl("http://url-to-license.com")
//            .build();
    	return new ApiInfoBuilder()
              .title("停车场模块文档")
              .description("停车场模块文档")
              .contact(new Contact("auth", "", "auth@qq.com"))
              .version("0.0.1")
              .build();
    	
    }


    @Bean
    public SmartInitializingSingleton smartInitializingSingleton() {
        return () -> {
            if (handlerMappings != null && handlerMappings.size() > 0) {
                for (HandlerMapping mappingHandlerMapping : handlerMappings) {
                    if (mappingHandlerMapping instanceof PropertySourcedRequestMappingHandlerMapping) {
                        Map<String, CorsConfiguration> corsConfigurations = new HashMap<>();
                        corsConfigurations.put("/**", new CorsConfiguration().applyPermitDefaultValues());
                        ((PropertySourcedRequestMappingHandlerMapping) mappingHandlerMapping).setCorsConfigurations(corsConfigurations);
                    }
                }
            }
        };
    }
}
