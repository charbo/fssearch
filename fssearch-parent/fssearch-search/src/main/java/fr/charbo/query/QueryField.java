package fr.charbo.query;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * The Interface QueryField.
 */
public interface QueryField {

  /**
   * Gets the query builder.
   *
   * @return the query builder
   */
  QueryBuilder getQueryBuilder();


  /**
   * Gets the fuzzy builder.
   *
   * @return the fuzzy builder
   */
  QueryBuilder getFuzzyBuilder();

}
