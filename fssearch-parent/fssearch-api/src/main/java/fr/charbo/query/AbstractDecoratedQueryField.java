package fr.charbo.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public abstract class AbstractDecoratedQueryField extends AbstractQueryField {

  /** The query field. */
  private final QueryField queryField;

  public AbstractDecoratedQueryField(final QueryField queryField, final Object value) {
    super(value);
    this.queryField = queryField;
  }

  public QueryBuilder getQueryBuilder() {
    return QueryBuilders.boolQuery().must(QueryBuilders.termQuery(getFieldName(), this.value)).must(this.queryField.getQueryBuilder());
  }

  public QueryBuilder getFuzzyBuilder() {
    return QueryBuilders.boolQuery().should(QueryBuilders.fuzzyQuery(getFieldName(), this.value)).should(this.queryField.getFuzzyBuilder());
  }

  protected abstract String getFieldName();
}
