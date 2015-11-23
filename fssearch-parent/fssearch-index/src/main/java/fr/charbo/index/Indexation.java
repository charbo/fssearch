package fr.charbo.index;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.io.File;

import java.nio.file.Paths;

@SpringBootApplication
@EnableScheduling
@ComponentScan
@PropertySources({
        @PropertySource(value = "classpath:default_parameters.properties"),
        @PropertySource(value = "classpath:custom_parameters.properties", ignoreResourceNotFound = true)
})
public class Indexation {


  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(final String[] args) {
    final ApplicationContext context = SpringApplication.run(Indexation.class, args);
    final Environment environment = context.getEnvironment();
    String path = environment.getProperty("fssearch.file.path");
    FileSystemObservable fileSystemObservable = (FileSystemObservable) context.getBean(FileSystemObservable.class);
    fileSystemObservable.subscribeActionIndex(Paths.get(new File(path).toURI()));
  }
}
