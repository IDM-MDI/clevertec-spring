package ru.clevertec.ecl.spring.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = {"ru.clevertec.ecl.spring"})
@EnableWebMvc
public class ClevertecSpringTaskApplication implements WebMvcConfigurer {
}
