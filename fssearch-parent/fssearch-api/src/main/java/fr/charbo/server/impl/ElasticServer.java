package fr.charbo.server.impl;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import fr.charbo.server.Client;
import fr.charbo.server.Server;


public class ElasticServer implements Server {
	
	private final Node node;
	
	private final Client client;
	
	private final String path;
	
	public ElasticServer(String path) {
		this.path = path;
		node = NodeBuilder
				.nodeBuilder()
				.settings(
						ImmutableSettings.builder().put("path.data", path)
								.build()).build();
		client = new ElasticClient(node);
	}
 
	@Override
	public void start() {
		if (node.isClosed()) {
			node.start();
		}
	}

	@Override
	public boolean isRunning() {
		return !node.isClosed();
	}

	@Override
	public void stop() {
		if (node != null && !isRunning()) {
			node.close();
		}
	}

	@Override
	public Client getClient() {
		return client;
	}

}
