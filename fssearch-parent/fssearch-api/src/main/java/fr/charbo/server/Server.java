package fr.charbo.server;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;

/**
 * The Interface Server.
 */
public interface Server {

  /**
   * Start.
   */
  void start();

  /**
   * Checks if is running.
   *
   * @return true, if is running
   */
  boolean isRunning();

  /**
   * Stop.
   */
  void stop();

  /**
   * Gets the client.
   *
   * @return the client
   */
  Client getClient();

  /**
   * Adds the river.
   *
   * @param river the river
   * @return the index response
   * @throws ElasticsearchException the elasticsearch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  IndexResponse addRiver(River river) throws ElasticsearchException, IOException;

  /**
   * Update river.
   *
   * @param river the river
   * @return the river
   * @throws ExecutionException
   * @throws InterruptedException
   * @throws IOException
   */
  River updateRiver(River river) throws InterruptedException, ExecutionException, IOException;

}
