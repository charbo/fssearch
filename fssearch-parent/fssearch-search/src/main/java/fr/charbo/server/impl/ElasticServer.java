package fr.charbo.server.impl;

import fr.charbo.client.ElasticClient;
import fr.charbo.client.SearchClient;
import fr.charbo.server.Server;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * The Class ElasticServer.
 */
public class ElasticServer implements Server {

  /** The searchClient. */
  private final SearchClient searchClient;

  /** The is running. */
  private boolean isRunning;

  private Client client;

  /**
   * Instantiates a new elastic server.
   *
   * @param url the client url
   */
  public ElasticServer(final String url) {
    this.client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(url, 9300));
    this.isRunning = true;
    this.searchClient = new ElasticClient(this.client);
  }


  /* (non-Javadoc)
   * @see fr.charbo.server.Server#isRunning()
   */
  @Override
  public boolean isRunning() {
    return this.isRunning;
  }


  /* (non-Javadoc)
   * @see fr.charbo.server.Server#close()
   */
  @Override
  public void close() {
    this.client.close();
    this.isRunning = false;
  }

  /* (non-Javadoc)
   * @see fr.charbo.server.Server#getSearchClient()
   */
  public SearchClient getSearchClient() {
    return this.searchClient;
  }

}
