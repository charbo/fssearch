package fr.charbo.server.impl;

import org.elasticsearch.node.Node;

import fr.charbo.server.Client;

/**
 * The Class ElasticClient.
 */
public class ElasticClient implements Client {

  /** The node. */
  private final Node node;

  /**
   * Instantiates a new elastic client.
   *
   * @param node the node
   */
  public ElasticClient(final Node node) {
    this.node = node;
  }


}
