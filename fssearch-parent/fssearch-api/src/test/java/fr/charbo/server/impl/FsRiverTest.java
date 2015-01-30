package fr.charbo.server.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The Class FsRiverTest.
 */
public class FsRiverTest {

  /** The Constant URL_INFOS. */
  private static final String URL_INFOS = "\"url\" : \"rootPath\"";

  /** The Constant INCLUDES_DOC. */
  private static final String INCLUDES_DOC = "\"includes\" : [ \"*.doc\" ]";

  /** The Constant INCLUDES_BOTH. */
  private static final String INCLUDES_BOTH = "\"includes\" : [ \"*.doc\", \"*.pdf\" ]";

  /** The Constant INCLUDES_PDF. */
  private static final String INCLUDES_PDF = "\"includes\" : [ \"*.pdf\" ]";

  /** The fs river. */
  private final FsRiver fsRiver = new FsRiver("name", "rootPath", 100);

  /**
   * Test get x content builder.
   *
   * @throws Exception the exception
   */
  @Test
  public void testGetXContentBuilder() throws Exception {
    this.fsRiver.addDocType("*.doc");

    String content = this.fsRiver.getXContentBuilder().string();
    assertTrue(content.contains(URL_INFOS));
    assertTrue(content.contains(INCLUDES_DOC));

    this.fsRiver.addDocType("*.pdf");
    content = this.fsRiver.getXContentBuilder().string();
    assertTrue(content.contains(INCLUDES_BOTH));

    this.fsRiver.removeDocType("*.doc");
    content = this.fsRiver.getXContentBuilder().string();
    assertTrue(content.contains(INCLUDES_PDF));
  }

}
