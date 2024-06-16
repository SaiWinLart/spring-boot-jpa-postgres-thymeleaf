//package com.springboot.jpa.hibernate;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring6.view.ThymeleafViewResolver;
//
////@Configuration
//public class ThymeleafConfig {
//
////    @Bean
////    public SpringResourceTemplateResolver templateResolver(){
////        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
////        templateResolver.setPrefix("/WEB-INF/views/");
////        templateResolver.setSuffix(".html");
////        templateResolver.setTemplateMode("HTML5");
////        return templateResolver;
////    }
//
////    @Bean
////    public SpringTemplateEngine templateEngine(){
////        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
////        templateEngine.setTemplateResolver(templateResolver());
////        return templateEngine;
////    }
//
////    @Bean
////    public ThymeleafViewResolver viewResolver(){
////        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
////        viewResolver.setTemplateEngine(templateEngine());
////        return viewResolver;
////    }
//}
