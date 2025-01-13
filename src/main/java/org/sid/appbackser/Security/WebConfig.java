package org.sid.appbackser.Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


   @Override
   public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**") // Allow all paths
               .allowedOrigins("http://127.0.0.1:5500") // Allow the frontend URL
               .allowedMethods("GET", "POST", "PUT", "DELETE","OPTION") // Allow methods
               .allowedHeaders("*")
               .exposedHeaders("Authorization") // Expose headers to the client
               .allowCredentials(true) // Allow cookies, etc.
               .maxAge(3600); // Cache preflight responses for 1 hour
   }
   
   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
       converters.add(new MappingJackson2HttpMessageConverter());
   }
   
}
