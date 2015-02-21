package fr.thery.poc.repository;

import static fr.thery.poc.entity.QPlayer.player;
import static fr.thery.poc.entity.QTeam.team;

import java.util.Collection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstantImpl;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.CaseBuilder;
import com.mysema.query.types.expr.StringExpression;
import com.mysema.query.types.template.StringTemplate;

import fr.thery.poc.dto.PlayerItemDto;
import fr.thery.poc.entity.Player;
import fr.thery.poc.utils.QuerydslSpringUtils;

/**
 * Player repository custom implementation using querydsl.
 *
 * @author Thery
 *
 */
public class PlayerRepositoryImpl extends QueryDslRepositorySupport implements
    PlayerRepositoryCustom {

  /**
   * Constructor.
   */
  public PlayerRepositoryImpl() {
    super(Player.class);
  }

  @Override
  public Collection<Player> getPlayers(Predicate predicate) {
    // First join is free but can but declared
    return from(player).join(player.team, team).where(predicate).list(player);
  }

  @Override
  public Collection<PlayerItemDto> getPlayerItems(Predicate predicate) {
    return getPlayerBaseQuery(predicate).list(
        ConstructorExpression.create(PlayerItemDto.class, player.id, player.firstname.concat(" ")
            .concat(player.lastname)));
  }


  @Override
  public Collection<PlayerItemDto> getPlayerItems(Predicate predicate, Pageable pageable) {
    JPQLQuery query = getPlayerBaseQuery(predicate);

    ConstructorExpression<PlayerItemDto> constructor =
        ConstructorExpression.create(PlayerItemDto.class, player.id, player.firstname.concat(" ")
            .concat(player.lastname));
    QuerydslSpringUtils.paginateAndSort(query, pageable, constructor);

    return query.list(constructor);
  }

  @Override
  public Collection<PlayerItemDto> getPlayerItemsReplacingZlatanByGod(Predicate predicate) {
    StringExpression zlatanCase =
        new CaseBuilder().when(player.firstname.equalsIgnoreCase("Zlatan")).then("God")
            .otherwise(player.firstname);

    return getPlayerBaseQuery(predicate).list(
        ConstructorExpression.create(PlayerItemDto.class, player.id,
            zlatanCase.concat(" ").concat(player.lastname)));
  }

  @Override
  public Collection<PlayerItemDto> getPlayerItemsUsingOracleNativeFunction(Predicate predicate) {
    // REGEXP_REPLACE : Oracle puts a space after each non-null character in the string.
    StringExpression oracleNativeRegexp =
        StringTemplate.create("REGEXP_REPLACE({0},{1},{2})", player.firstname,
            ConstantImpl.create("(.)"), ConstantImpl.create("\1 "));

    return getPlayerBaseQuery(predicate).list(
        ConstructorExpression.create(PlayerItemDto.class, player.id, oracleNativeRegexp));
  }

  /**
   * Retrieve player base query, mostly used for dynamic queries.
   *
   * @param predicate {@link Predicate}
   * @return {@link JPQLQuery}
   */
  private JPQLQuery getPlayerBaseQuery(Predicate predicate) {
    return from(player).where(predicate);
  }

}
