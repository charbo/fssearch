package fr.charbo.server;

import java.io.IOException;
import java.util.Set;

import org.elasticsearch.common.xcontent.XContentBuilder;


/**
 * The Interface FsRiver.
 */
public interface River {

  /**
   * Gets the root path.
   * Used to set "url" in FsRiver definition.
   * @return the root path
   */
  String getRootPath();

  /**
   * Gets the update rate.
   *
   * @return the update rate
   */
  Integer getUpdateRate();
  
  void setUpdateRate(Integer updateRate);

  /**
   * Gets the name.
   *
   * @return the name
   */
  String getName();

  /**
   * Gets the storedtype.
   *
   * @return the current Stored Types
   */
  Set<String> getStoredtype();

  /**
   * Adds doc type in storedtype.
   *
   * @param docType the doc type
   * @return the current Stored Types
   */
  Set<String> addDocType(String docType);

  /**
   * Removes the doc type.
   *
   * @param docType the doc type
   * @return the current Stored Types
   */
  Set<String> removeDocType(String docType);
  
  /**
   * Gets the Excludedpath.
   *
   * @return the current Excluded Paths
   */
  Set<String> getExcludedPaths();

  /**
   * Adds doc path in Excludedpath.
   *
   * @param ExcludedPath the doc path
   * @return the current Excluded Paths
   */
  Set<String> addExcludedPath(String excludedPath);

  /**
   * Removes the doc path.
   *
   * @param ExcludedPath the doc path
   * @return the current Excluded Paths
   */
  Set<String> removeExcludedPath(String excludedPath);

  /**
   * Gets the x content builder.
   *
   * @return the x content builder
   * @throws IOException
   */
  XContentBuilder getXContentBuilder() throws IOException;




}
