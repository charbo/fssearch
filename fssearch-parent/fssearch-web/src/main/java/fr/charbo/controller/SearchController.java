package fr.charbo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.charbo.query.result.Document;
import fr.charbo.service.SearchService;

/**
 * The Class SearchController.
 */
@RestController
public class SearchController {

  /** The search service. */
  @Autowired
  private SearchService searchService;

  /**
   * Execute search.
   *
   * @param query the query
   * @return the list
   */
  @RequestMapping("/search")
  public List<Document> executeSearch(@RequestParam(value="query", defaultValue="*") final String query) {
    return this.searchService.search(query);
  }

}
