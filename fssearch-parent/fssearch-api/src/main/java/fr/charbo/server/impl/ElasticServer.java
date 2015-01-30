package fr.charbo.server.impl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fr.charbo.server.Client;
import fr.charbo.server.River;
import fr.charbo.server.Server;

/**
 * The Class ElasticServer.
 */
@Component
public class ElasticServer implements Server {

  /** The node. */
  private final Node node;

  /** The client. */
  private final Client client;

  /**
   * Instantiates a new elastic server.
   *
   * @param path the path
   */
  @Autowired
  public ElasticServer(@Value("${fssearch.elastisearch.server.path}") final String path) {
    this.node = NodeBuilder.nodeBuilder().settings(ImmutableSettings.builder().put("path.data", path).build()).build();
    this.client = new ElasticClient(this.node);
  }

  /* (non-Javadoc)
   * @see fr.charbo.server.Server#addRiver(fr.charbo.server.River)
   */
  @Override
  public void addRiver(final River river) throws ElasticsearchException, IOException {
    this.node.client().prepareIndex("_river", river.getName(), "_meta").setSource(river.getXContentBuilder()).execute().actionGet();
    //TODO use IndexResponse as return type

  }

  /* (non-Javadoc)
   * @see fr.charbo.server.Server#updateRiver(fr.charbo.server.River)
   */
  @Override
  public River updateRiver(final River river) throws InterruptedException, ExecutionException, IOException {
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
   * @see fr.charbo.server.Server#start()
   */
  @Override
  public void start() {
    if (this.node.isClosed()) {
      this.node.start();
    }
  }

  /* (non-Javadoc)
   * @see fr.charbo.server.Server#isRunning()
   */
  @Override
  public boolean isRunning() {
    return !this.node.isClosed();
  }

  /* (non-Javadoc)
   * @see fr.charbo.server.Server#stop()
   */
  @Override
  public void stop() {
    if ((this.node != null) && this.isRunning()) {
      this.node.close();
    }
  }

  /* (non-Javadoc)
   * @see fr.charbo.server.Server#getClient()
   */
  @Override
  public Client getClient() {
    return this.client;
  }

}
