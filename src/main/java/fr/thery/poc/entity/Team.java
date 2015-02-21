package fr.thery.poc.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Team entity.
 *
 * @author Thery
 *
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@SequenceGenerator(name = "SEQ_TEAM", initialValue = 1)
public class Team extends AbstractEntity {

  /** Serial version. */
  private static final long serialVersionUID = 949107140639444746L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TEAM")
  @Column(name = "TEM_ID")
  private Integer id;

  @Column(name = "TEM_NAME", length = 255, nullable = false)
  private String name;

  @OneToMany(mappedBy = "team")
  private Collection<Player> players;

  @ManyToOne
  @JoinColumn(name = "SPR_ID")
  private Sponsor sponsor;

}
