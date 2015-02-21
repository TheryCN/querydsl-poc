package fr.thery.poc.repository;

import java.util.Collection;

import org.springframework.data.domain.Pageable;

import com.mysema.query.types.Predicate;

import fr.thery.poc.dto.PlayerItemDto;
import fr.thery.poc.entity.Player;

/**
 * Player repository custom interface.
 *
 * @author Thery
 *
 */
public interface PlayerRepositoryCustom {

  /**
   * Load players from a predicate.
   *
   * @param predicate {@link Predicate}
   * @return the players
   */
  public Collection<Player> getPlayers(Predicate predicate);

  /**
   * Load player items from a predicate
   *
   * @param predicate {@link Predicate}
   * @return the player items
   */
  public Collection<PlayerItemDto> getPlayerItems(Predicate predicate);

  /**
   * Load player items from a predicate
   *
   * @param predicate {@link Predicate}
   * @param pageable {@link Pageable}
   * @return the player items
   */
  public Collection<PlayerItemDto> getPlayerItems(Predicate predicate, Pageable pageable);

  /**
   * Load player items from a predicate but replace Zlatan by God.
   *
   * @param predicate {@link Predicate}
   * @return the player items
   */
  public Collection<PlayerItemDto> getPlayerItemsReplacingZlatanByGod(Predicate predicate);

  /**
   * Load player items from a predicate & using oracle native function.
   *
   * @param predicate {@link Predicate}
   * @return the player items
   */
  public Collection<PlayerItemDto> getPlayerItemsUsingOracleNativeFunction(Predicate predicate);
}
