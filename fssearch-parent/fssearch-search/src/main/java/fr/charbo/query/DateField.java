package fr.charbo.query;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * The Class DateField.
 */
public class DateField extends AbstractDecoratedQueryField implements QueryField {
  /** The Constant FIELD_NAME. */
  private static final String FIELD_NAME = "date";

  /**
   * Instantiates a new date field.
   *
   * @param queryField the query field
   * @param value the value
   */
  public DateField(final QueryField queryField, final Object value) {
    super(queryField, value);
  }

  /* (non-Javadoc)
   * @see fr.charbo.query.QueryField#getQueryBuilder()
   */
  @Override
  public QueryBuilder getQueryBuilder() {
    return super.getQueryBuilder();
  }

  /* (non-Javadoc)
   * @see fr.charbo.query.QueryField#getFuzzyBuilder()
   */
  @Override
  public QueryBuilder getFuzzyBuilder() {
    return super.getFuzzyBuilder();
  }

  @Override
  protected String getFieldName() {
    return FIELD_NAME;
  }
}
