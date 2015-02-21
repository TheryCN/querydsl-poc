package fr.thery.poc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * {@link PlayerItemDto} used to populate combobox using key/value combo.
 *
 * @author Thery
 *
 */
@Data
@AllArgsConstructor
public class PlayerItemDto implements DataTransfertObject {

  /** Serial version. */
  private static final long serialVersionUID = -890160643464818284L;

  /* Player id. */
  private Integer id;

  /* Concatenation of firstname & lastname. */
  private String name;
}
