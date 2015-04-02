package fr.charbo.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.io.FileSystemUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.charbo.SpringConfiguration;
import fr.charbo.server.impl.ElasticServer;
import fr.charbo.server.impl.FsRiver;

/**
 * The Class ServerTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringConfiguration.class)
public class ServerTest {

  /** The server. */
  private Server server;

  /** The river. */
  private River river;

  /** The path. */
  @Value("${fssearch.elastisearch.server.path}")
  private String path;

  /** The river path. */
  @Value("${fssearch.default.river.path}")
  private String riverPath;


  /**
   * Inits the.
   */
  @Before
  public void init() {
    this.server = new ElasticServer(this.path);
    this.river = new FsRiver("test_river", this.riverPath, 15);
  }


  /**
   * Test init.
   *
   * @throws Exception the exception
   */
  @Test
  @Ignore
  public void testInit() throws Exception {
    try {
      assertNotNull(this.server.getClient());

      final IndexResponse response = this.server.addRiver(this.river);
      assertTrue(response.isCreated());

      this.server.close();
      assertFalse(this.server.isRunning());
    } finally {
      this.server.close();
      FileSystemUtils.deleteRecursively(new File(this.path));
    }
  }


}
