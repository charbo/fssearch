package fr.charbo.server.impl;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;

import fr.charbo.server.River;
import fr.charbo.server.SearchEngine;

/**
 * The Class SearchEngineBuilder.
 */
public class SearchEngineBuilder {

	/** The search engine. */
	private static SearchEngineImpl SEARCH_ENGINE;


	private River river;

	/**
	 * Instantiates a new search engine builder.
	 *
	 * @param indexPath
	 *            the index path
	 */
	public SearchEngineBuilder(final String indexPath, final String name, final String rootPath) {
		if (SEARCH_ENGINE == null) {
			SEARCH_ENGINE = new SearchEngineImpl(indexPath);
			river = SEARCH_ENGINE.initializeDefaultRiver(name,	rootPath);
		}
	}


	/**
	 * Sets the update rate.
	 *
	 * @param updateRate
	 *            the new update rate
	 */
	public SearchEngineBuilder updateRate(final Integer updateRate) {
		river.setUpdateRate(updateRate);
		return this;
	}
	
	public SearchEngineBuilder storedType(final String storedType) {
		for (String docType : storedType.split(";")) {
			river.addDocType(docType);
		}
		return this;
	}
	
	
	public SearchEngineBuilder excludedPaths(final String excludedPaths) {
		for (String excludedPath : excludedPaths.split(";")) {
			river.addExcludedPath(excludedPath);
		}
		return this;
	}

	/**
	 * Builds the.
	 *
	 * @return the search engine
	 */
	public SearchEngine build() throws ElasticsearchException, IOException {
		SEARCH_ENGINE.launchRiver();
		return SEARCH_ENGINE;
	}
}
