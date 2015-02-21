package fr.thery.poc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Soccer players entity.
 *
 * @author Thery
 *
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@SequenceGenerator(name = "SEQ_PLAYER", initialValue = 1)
public class Player extends AbstractEntity {

  /** Serial number. */
  private static final long serialVersionUID = 5177228248327746374L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLAYER")
  @Column(name = "PLR_ID")
  private Integer id;

  @Column(name = "PLR_FIRSTNAME", length = 255, nullable = false)
  private String firstname;

  @Column(name = "PLR_LASTNAME", length = 255, nullable = false)
  private String lastname;

  @ManyToOne(optional = true)
  @JoinColumn(name = "TEM_ID")
  private Team team;
}
