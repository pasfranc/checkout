package com.r3pi.assessment.checkout;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class CheckoutApplication {

  private static final String I18N_FOLDER = "i18n/messages";
  
  public static void main(String[] args) {
    SpringApplication.run(CheckoutApplication.class, args);
  }
  
  @Bean
  public Docket swaggerSettings() {
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
        .apis(RequestHandlerSelectors.any()).paths(regex("/api/.*")).build()
        .useDefaultResponseMessages(false);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Checkout API")
        .description("Provides Checkout API").version("1.0.0").build();
  }
  
  public void addViewControllers(ViewControllerRegistry registry) {
      registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs");
      registry.addRedirectViewController("/api/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
      registry.addRedirectViewController("/api/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
      registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
  }

  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/api/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
      registry.addResourceHandler("/api/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
  
  @Bean
  public ResourceBundleMessageSource messageSource() {
   ResourceBundleMessageSource source = new ResourceBundleMessageSource();
   source.setBasenames(I18N_FOLDER);  // name of the resource bundle 
   source.setUseCodeAsDefaultMessage(true);
   return source;
  }
  
  @Bean
  public LocaleResolver localeResolver() {
      SessionLocaleResolver slr = new SessionLocaleResolver();
      slr.setDefaultLocale(Locale.ENGLISH);
      return slr;
  }

}
