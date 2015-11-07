package fr.charbo.server;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import fr.charbo.client.SearchClient;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;

/**
 * The Interface Server.
 */
public interface Server {


  /**
   * Checks if is running.
   *
   * @return true, if is running
   */
  boolean isRunning();


  /**
   * Gets the client.
   *
   * @return the client
   */
  SearchClient getSearchClient();

  /**
   * Close the Node.
   */
  void close();

}
