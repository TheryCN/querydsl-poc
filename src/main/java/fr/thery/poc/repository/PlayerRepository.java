package fr.thery.poc.repository;

import fr.thery.poc.Repository;
import fr.thery.poc.entity.Player;

/**
 * Player repository.
 *
 * @author Thery
 *
 */
public interface PlayerRepository extends Repository<Player, Integer>, PlayerRepositoryCustom {

}
