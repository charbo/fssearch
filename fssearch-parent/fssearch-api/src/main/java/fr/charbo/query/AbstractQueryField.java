package fr.charbo.query;


public abstract class AbstractQueryField implements QueryField {

  protected final Object value;

  public AbstractQueryField(final Object value) {
    this.value = value;
  }


}
