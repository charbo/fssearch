package fr.charbo.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import fr.charbo.configuration.DataBaseConfiguration;
import fr.charbo.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DataBaseConfiguration.class)
@TransactionConfiguration(defaultRollback = true)
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testInit() throws Exception {
    final User user = new User();;
    System.out.println(this.userRepository.save(user));

  }

}
