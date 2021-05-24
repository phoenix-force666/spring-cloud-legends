/**
 * 
 */
package top.legendscloud.web.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.base.Predicate;
import top.legendscloud.web.service.LegendBaseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import io.swagger.models.Contact;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhaopeng
 * descrption: The config file of swagger
 * 20181219
 */
@EnableSwagger2
@SpringBootConfiguration
@Profile({"dev", "test", "sit", "uat"})
@EnableSwaggerBootstrapUI
public class Swagger2Config {
    
    @Autowired
    private LegendBaseConfigService legendsBaseConfig;
    
    @Bean
    public Docket createRestApi() {
        String basePackage=legendsBaseConfig.getSwBasePack();
        Predicate<RequestHandler> predicate=null;
        if(basePackage.equals("*")){
            predicate=RequestHandlerSelectors.any();
        }else{
            predicate=RequestHandlerSelectors.basePackage(basePackage);
        }
        RequestHandlerSelectors.any();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(predicate)
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createDemoRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("示例API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.legendscloud.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
	Contact contact = new Contact();
	contact.setName(legendsBaseConfig.getSwName());
	contact.setEmail(legendsBaseConfig.getSwEmail());
	contact.setUrl(legendsBaseConfig.getSwUrl());
        return new ApiInfoBuilder()
                .title(legendsBaseConfig.getSwTitle())
                .description(legendsBaseConfig.getSwDesc())
                .version(legendsBaseConfig.getSwVers())
                .contact(legendsBaseConfig.getSwName())
                .build();
    }

}
