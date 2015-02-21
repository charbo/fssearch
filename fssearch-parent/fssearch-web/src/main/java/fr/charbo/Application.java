package fr.charbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * The Class Application.
 */
@SpringBootApplication
@PropertySources({
  @PropertySource(value = "classpath:default_parameters.properties", ignoreResourceNotFound = true),
  @PropertySource(value = "classpath:custom_parameters.properties", ignoreResourceNotFound = true)
})
public class Application extends SpringBootServletInitializer {

  /* (non-Javadoc)
   * @see org.springframework.boot.context.web.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
   */
  @Override
  protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
