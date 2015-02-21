package fr.charbo.service;

import java.util.List;

import fr.charbo.query.result.Document;

/**
 * The Interface SearchService.
 */
public interface SearchService {

  /**
   * Search.
   *
   * @param query the query
   * @return the list
   */
  List<Document> search(String query);

}
