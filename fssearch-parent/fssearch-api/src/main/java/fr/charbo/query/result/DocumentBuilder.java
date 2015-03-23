/*
 *
 */
package fr.charbo.query.result;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.springframework.beans.DirectFieldAccessor;

/**
 * The Class DocumentBuilder.
 * This class is used to instantiate document from a SearchResponse.
 */
public class DocumentBuilder {

  /**
   * Creates the document.
   *
   * @param values the values
   * @return the document
   */
  protected static final Document createDocument(final Map<String, SearchHitField> values) {
    final Document result = new Document();
    final Field[] fields = Document.class.getDeclaredFields();

    for (final Field field : fields) {
      final Mapping anno = field.getAnnotation(Mapping.class);
      if (anno != null) {
        final SearchHitField value = values.get(anno.key());
        final DirectFieldAccessor myObjectAccessor = new DirectFieldAccessor(result);
        //TODO check why null
        if (field != null && value != null) {
        	myObjectAccessor.setPropertyValue(field.getName(), value.getValue());
        }
      }
    }

    return result;

  }

  /**
   * Extract documents.
   *
   * @param response the response
   * @return the list
   */
  public static final List<Document> extractDocuments(final SearchResponse response) {
    final List<Document> result = new ArrayList<Document>();

    for (final SearchHit hit : response.getHits()) {
      final Document document = createDocument(hit.getFields());
      result.add(document);
    }

    return result;
  }

}
