package fr.charbo.query;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * The Class ContentField.
 */
public class ContentField extends AbstractQueryField implements QueryField {

  /** The Constant FIELD_NAME. */
  private static final String FIELD_NAME = "content";

  /**
   * Instantiates a new content field.
   *
   * @param value the value
   */
  public ContentField(final Object value) {
    super(value);
  }



  /* (non-Javadoc)
   * @see fr.charbo.query.QueryField#getQueryBuilder()
   */
  @Override
  public QueryBuilder getQueryBuilder() {
    return QueryBuilders.termQuery(FIELD_NAME, this.value);
  }



  /* (non-Javadoc)
   * @see fr.charbo.query.QueryField#getFuzzyBuilder()
   */
  @Override
  public QueryBuilder getFuzzyBuilder() {
    return QueryBuilders.fuzzyQuery(FIELD_NAME, this.value).fuzziness(Fuzziness.TWO);
  }

}
