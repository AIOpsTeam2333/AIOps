package com.aiops.api.controller;

import com.aiops.api.common.kpi.KpiIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-03 13:24
 **/
@ApiIgnore
@Controller
@RequestMapping
@Configuration
@EnableSwagger2
public class Swagger2Controller {

    /**
     * 首页转发到swagger2
     */
    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/swagger-ui.html");
        return modelAndView;
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.aiops.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Timestamp.class, Long.class)
                .directModelSubstitute(KpiIndicator.class, String.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("AiOps 展示数据API")
                //创建人
                .contact(new Contact("姚帅宇", "http://wpa.qq.com/msgrd?v=3&uin=260386109&site=qq&menu=yes", "260386109@qq.com"))
                //版本号
                .version("1.0")
                //描述
                .description("API 接口描述")
                .build();
    }
}
