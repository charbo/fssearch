package fr.charbo.server.impl;

import org.elasticsearch.node.Node;

import fr.charbo.server.Client;

public class ElasticClient implements Client {
	
	private final Node node;
	
	public ElasticClient(Node node) {
		this.node = node;
	}

}
