package fr.charbo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The Class User.
 */
@Entity(name = "USERS")
public class User {

  /** The id. */
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;

  /** The name. */
  @Column(name = "NAME")
  private String name;

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public long getId() {
    return this.id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(final long id) {
    this.id = id;
  }





}
