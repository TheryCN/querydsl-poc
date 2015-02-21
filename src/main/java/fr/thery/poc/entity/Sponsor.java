package fr.thery.poc.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Sponsor entity.
 *
 * @author Thery
 *
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@SequenceGenerator(name = "SEQ_SPONSOR", initialValue = 1)
public class Sponsor extends AbstractEntity {

  /** Serial version. */
  private static final long serialVersionUID = 9186784321689241609L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SPONSOR")
  @Column(name = "SPR_ID")
  private Integer id;

  @Column(name = "SPR_NAME", length = 255, nullable = false)
  private String name;

  @OneToMany(mappedBy = "sponsor")
  private Collection<Team> teams;
}
