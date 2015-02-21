package fr.thery.poc.repository;

import static fr.thery.poc.entity.QPlayer.player;
import static fr.thery.poc.entity.QTeam.team;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

import fr.thery.poc.dto.PlayerItemDto;
import fr.thery.poc.entity.Player;
import fr.thery.poc.utils.QuerydslSpringUtils;

/**
 * {@link PlayerRepositoryImpl} test class.
 *
 * @author Thery
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-context-test.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
/** Init database. */
@DatabaseSetup("/META-INF/dataset/playerRepositorySetup.xml")
/** Clean database at the end. */
@DatabaseTearDown(value = "/META-INF/dataset/playerRepositorySetup.xml",
    type = DatabaseOperation.DELETE)
public class PlayerRepositoryImplTest {

  /** Constant for player having a team sponsorised by Adidas. */
  private static final int ADIDAS_PLAYERS_NUMBER = 4;

  /** Constant for player having thiago as firstname. */
  private static final int THIAGO_PLAYERS_NUMBER = 2;

  /** Constant for total players number. */
  private static final int PLAYERS_NUMBER = 9;

  @Inject
  private PlayerRepository playerDao;

  /**
   * Test loading all players without predicates.
   */
  @Test
  public void testGetPlayersLoadingAll() {
    Collection<Player> players = playerDao.getPlayers(null);

    assertEquals(PLAYERS_NUMBER, players.size());
  }

  /**
   * Test loading all players having an Adidas team (First join is free & join between player & team
   * was declared, then we can join team with sponsor without any declaration).
   */
  @Test
  public void testGetPlayersLoadingAdidas() {
    Collection<Player> players = playerDao.getPlayers(team.sponsor.name.equalsIgnoreCase("Adidas"));

    assertEquals(ADIDAS_PLAYERS_NUMBER, players.size());
  }

  /**
   * Test loading players with firstname "Thiago".
   */
  @Test
  public void testGetPlayersLoadingThiago() {
    Collection<Player> players = playerDao.getPlayers(player.firstname.eq("Thiago"));

    assertEquals(THIAGO_PLAYERS_NUMBER, players.size());
    for (Player player : players) {
      assertEquals("Thiago", player.getFirstname());
    }
  }

  /**
   * Test loading all players <id, firstname+lastname> witout predicates.
   */
  @Test
  public void testGetPlayerItemsLoadingAll() {
    Collection<PlayerItemDto> players = playerDao.getPlayerItems(null);

    assertEquals(PLAYERS_NUMBER, players.size());
  }

  /**
   * Test loading all players with pagination and sorting.
   */
  @Test
  public void testGetPlayerItemsUsingPaginationAndSort() {
    // Loading first page of 3 elements
    Pageable pager = QuerydslSpringUtils.createPager(0, 3, "name", Direction.DESC);
    List<PlayerItemDto> players = (List<PlayerItemDto>) playerDao.getPlayerItems(null, pager);

    assertEquals(players.size(), 3);
    assertEquals(players.get(0).getName(), "Zlatan Ibrahimovic");
    assertEquals(players.get(1).getName(), "Wayne Rooney");
    assertEquals(players.get(2).getName(), "Thiago Silva");
  }

  /**
   * Test load all players without predicates and check that zlatan is replacing by god as
   * firstname.
   */
  @Test
  public void testGetPlayerItemsReplacingZlatanByGod() {
    Collection<PlayerItemDto> players = playerDao.getPlayerItemsReplacingZlatanByGod(null);

    for (PlayerItemDto player : players) {
      if (player.getName().contains("Ibrahimovic")) {
        assertEquals("God Ibrahimovic", player.getName());
      }
    }

    assertEquals(PLAYERS_NUMBER, players.size());
  }

  /**
   * Of course this method cannot be test with HSQLDB, InvalidDataAccessApiUsageException will be
   * thrown.
   */
  @Test(expected = InvalidDataAccessApiUsageException.class)
  public void testGetPlayerItemsUsingOracleNativeFunction() {
    playerDao.getPlayerItemsUsingOracleNativeFunction(null);
  }

}
