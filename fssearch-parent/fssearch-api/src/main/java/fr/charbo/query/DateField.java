package fr.charbo.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * The Class DateField.
 */
public class DateField extends AbstractQueryField implements QueryField {
  /** The Constant FIELD_NAME. */
  private static final String FIELD_NAME = "date";

  /** The query field. */
  private final QueryField queryField;

  /**
   * Instantiates a new date field.
   *
   * @param queryField the query field
   * @param value the value
   */
  public DateField(final QueryField queryField, final Object value) {
    super(value);
    this.queryField = queryField;
  }

  /* (non-Javadoc)
   * @see fr.charbo.query.QueryField#getQueryBuilder()
   */
  @Override
  public QueryBuilder getQueryBuilder() {
    return QueryBuilders.boolQuery().must(QueryBuilders.termQuery(FIELD_NAME, this.value)).must(this.queryField.getQueryBuilder());
  }

  /* (non-Javadoc)
   * @see fr.charbo.query.QueryField#getFuzzyBuilder()
   */
  @Override
  public QueryBuilder getFuzzyBuilder() {
    return QueryBuilders.boolQuery().must(QueryBuilders.fuzzyQuery(FIELD_NAME, this.value)).must(this.queryField.getFuzzyBuilder());
  }

}
