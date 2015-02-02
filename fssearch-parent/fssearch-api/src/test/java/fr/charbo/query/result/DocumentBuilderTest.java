package fr.charbo.query.result;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.internal.InternalSearchHitField;
import org.junit.Test;

/**
 * The Class DocumentBuilderTest.
 */
public class DocumentBuilderTest {


  /**
   * Testcreate document.
   *
   * @throws Exception the exception
   */
  @Test
  public void testcreateDocument() throws Exception {
    final List<Object> title = new ArrayList<Object>();
    title.add("Toto");
    final SearchHitField sf = new InternalSearchHitField("title", title );

    final Map<String, SearchHitField> values = new HashMap<String, SearchHitField>();
    values.put("title", sf);

    final Document document = DocumentBuilder.createDocument(values);
    assertEquals("Toto", document.getTitle());

  }

}
