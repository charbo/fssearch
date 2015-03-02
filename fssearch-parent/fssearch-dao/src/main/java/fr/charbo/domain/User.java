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
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /** The name. */
  @Column(name = "EMAIL")
  private String email;

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


  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    User user = (User) o;

    if (email != null ? !email.equals(user.email) : user.email != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    return email != null ? email.hashCode() : 0;
  }
}
