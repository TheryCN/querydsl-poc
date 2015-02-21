package fr.thery.poc.entity;

/**
 * Abstract ENtity.
 * 
 * @author Thery
 *
 */
public abstract class AbstractEntity implements Entity {

  /** Serial number. */
  private static final long serialVersionUID = 6458828878114759052L;

  /**
   * Retrieve entity'id.
   *
   * @return {@Integer} id
   */
  abstract Integer getId();

}
