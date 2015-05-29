package fr.charbo.server.impl;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;

import fr.charbo.server.River;

/**
 * The Class FsRiver.
 */
public class FsRiver implements River {

	/** The name. */
	private final String name;

	/** The root path. */
	private final String rootPath;

	/** The update rate. */
	private Integer updateRate;

	/** The stored type. */
	private final Set<String> storedType = new HashSet<String>();

	private final Set<String> excludedPaths = new HashSet<String>();

	/**
	 * Instantiates a new fs river.
	 *
	 * @param name
	 *            the name
	 * @param rootPath
	 *            the root path
	 * @param updateRate
	 *            the update rate
	 */
	public FsRiver(final String name, final String rootPath) {
		this.name = name;
		this.rootPath = rootPath;
	}
	

	@Override
	public void setUpdateRate(Integer updateRate) {
		this.updateRate = updateRate;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.charbo.server.River#getRootPath()
	 */
	@Override
	public String getRootPath() {
		return this.rootPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.charbo.server.River#getUpdateRate()
	 */
	@Override
	public Integer getUpdateRate() {
		return this.updateRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.charbo.server.River#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.charbo.server.River#getStoredtype()
	 */
	@Override
	public Set<String> getStoredtype() {
		return ObjectUtils.clone(this.storedType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.charbo.server.River#addDocType(java.lang.String)
	 */
	@Override
	public Set<String> addDocType(final String docType) {
		this.storedType.add(docType);
		return this.getStoredtype();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.charbo.server.River#removeDocType(java.lang.String)
	 */
	@Override
	public Set<String> removeDocType(final String docType) {
		this.storedType.remove(docType);
		return this.getStoredtype();
	}

	@Override
	public Set<String> getExcludedPaths() {
		return ObjectUtils.clone(this.excludedPaths);
	}

	@Override
	public Set<String> addExcludedPath(String excludedPath) {
		this.excludedPaths.add(excludedPath);
		return this.getExcludedPaths();
	}

	@Override
	public Set<String> removeExcludedPath(String excludedPath) {
		this.storedType.remove(excludedPath);
		return this.getExcludedPaths();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.charbo.server.River#getXContentBuilder()
	 */
	@Override
	public XContentBuilder getXContentBuilder() throws IOException {
		final XContentBuilder river = jsonBuilder().prettyPrint().startObject()
				.field("type", "fs").startObject("fs")
				.field("url", this.rootPath)
				.field("update_rate", this.updateRate);
		if (!this.storedType.isEmpty()) {
			river.array("includes", this.storedType.toArray());
		}
		if (!this.excludedPaths.isEmpty()) {
			river.array("excludes", this.excludedPaths.toArray());
		}
		
		river.endObject().startObject("index").field("bulk_size", 1)
				.endObject().endObject();

		return river;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final FsRiver other = (FsRiver) obj;
		return Objects.equals(this.name, other.name);
	}

}
