package fr.charbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.transaction.TransactionConfiguration;

import fr.charbo.configuration.DataBaseConfiguration;

/**
 * The Class Application.
 */
@SpringApplicationConfiguration(classes = DataBaseConfiguration.class)
@TransactionConfiguration(defaultRollback = true)
@ComponentScan
public class Application {


  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
