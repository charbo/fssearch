package fr.charbo.client;

import org.elasticsearch.index.query.QueryBuilder;

import fr.charbo.query.result.SearchResult;

public interface SearchClient {

  long resultsCount(QueryBuilder queryBuilder, String... rivers);

  SearchResult search(QueryBuilder queryBuilder, String... rivers);

  SearchResult search(QueryBuilder queryBuilder, int begin, int pageSize, String... rivers);



}
