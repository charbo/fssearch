package fr.charbo.query;

import org.elasticsearch.index.query.QueryBuilder;

public class TitleField extends  AbstractDecoratedQueryField implements QueryField {
  /** The Constant FIELD_NAME. */
  private static final String FIELD_NAME = "title";

  public TitleField(QueryField queryField, Object value) {
    super(queryField, value);
  }

  @Override
  public QueryBuilder getQueryBuilder() {
    return super.getQueryBuilder();
  }

  @Override
  public QueryBuilder getFuzzyBuilder() {
    return super.getFuzzyBuilder();
  }

  @Override
  protected String getFieldName() {
    return FIELD_NAME;
  }
}
