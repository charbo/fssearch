package fr.charbo.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.elasticsearch.common.io.FileSystemUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.charbo.SpringConfiguration;
import fr.charbo.query.ContentField;
import fr.charbo.query.result.Document;
import fr.charbo.server.River;
import fr.charbo.server.Server;

/**
 * The Class ClientTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringConfiguration.class)
public class ClientTest {

  /** The Constant ONE. */
  private static final long ONE = 1;

  /** The Constant ZERO. */
  private static final long ZERO = 0;

  /** The server. */
  @Autowired
  private Server server;

  /** The river. */
  @Autowired
  private River river;

  /** The path. */
  @Value("${fssearch.elastisearch.server.path}")
  private String path;

  /**
   * Test count result.
   *
   * @throws Exception the exception
   */
  @Test
  public void testQuerying() throws Exception {
    FileSystemUtils.deleteRecursively(new File(this.path));
    final String index = "test_river";
    this.river.addDocType("*.txt");
    this.server.addRiver(this.river);
    Thread.sleep(18000);

    final ContentField dimanche = new ContentField("dimanche");
    assertEquals(ONE,
        this.server.getClient().resultsCount(QueryBuilders.boolQuery().must(dimanche.getQueryBuilder()), index));

    final List<Document> docs1 = this.server.getClient().search(dimanche.getQueryBuilder(), index).getDocuments();
    assertNotNull(docs1);
    assertEquals(1, docs1.size());
    assertEquals("sample.txt", docs1.get(0).getTitle());


    final ContentField dimancheMaj = new ContentField("Dimanche");
    assertEquals(ZERO,
        this.server.getClient().resultsCount(QueryBuilders.boolQuery().must(dimancheMaj.getQueryBuilder()), index));

    assertEquals(ONE,
        this.server.getClient().resultsCount(QueryBuilders.boolQuery().must(dimancheMaj.getFuzzyBuilder()), index));




  }

}
