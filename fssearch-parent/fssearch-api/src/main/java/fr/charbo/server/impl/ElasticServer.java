package fr.charbo.server.impl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import fr.charbo.client.Client;
import fr.charbo.client.ElasticClient;
import fr.charbo.server.River;
import fr.charbo.server.Server;

/**
 * The Class ElasticServer.
 */
public class ElasticServer implements Server {

  /** The node. */
  private final Node node;

  /** The client. */
  private final Client client;

  /** The is running. */
  private boolean isRunning;

  /**
   * Instantiates a new elastic server.
   *
   * @param path the path
   */
  public ElasticServer(final String path) {
    this.node = NodeBuilder.nodeBuilder().settings(ImmutableSettings.builder().put("path.data", path).build()).node();
    this.isRunning = true;
    this.client = new ElasticClient(this.node);
  }

  /* (non-Javadoc)
   * @see fr.charbo.server.Server#addRiver(fr.charbo.server.River)
   */
  @Override
  public IndexResponse addRiver(final River river) throws ElasticsearchException, IOException {
    return this.node.client().prepareIndex("_river", river.getName(), "_meta").setSource(river.getXContentBuilder()).execute().actionGet();

  }

  /* (non-Javadoc)
   * @see fr.charbo.server.Server#updateRiver(fr.charbo.server.River)
   */
  @Override
  public River updateRiver(final River river) throws InterruptedException, ExecutionException, IOException {
    //TODO update rivers
    final UpdateRequest updateRequest = new UpdateRequest();
    updateRequest.index("_river");
    updateRequest.type(river.getName());
    updateRequest.id("_meta");
    updateRequest.doc(river.getXContentBuilder());
    this.node.client().update(updateRequest).get();
    //    TODO use IndexResponse as return type
    return null;
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
    this.node.close();
    this.isRunning = false;
  }

  /* (non-Javadoc)
   * @see fr.charbo.server.Server#getClient()
   */
  @Override
  public Client getClient() {
    return this.client;
  }

}
