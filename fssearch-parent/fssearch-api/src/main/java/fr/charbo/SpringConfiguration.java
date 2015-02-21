package fr.charbo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
  @PropertySource(value = "classpath:default_parameters.properties", ignoreResourceNotFound = true),
  @PropertySource(value = "classpath:custom_parameters.properties", ignoreResourceNotFound = true)
})
@ComponentScan
@EnableAutoConfiguration
public class SpringConfiguration {

}
