package fr.thery.poc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.mysema.query.types.expr.ComparableExpressionBase;

import fr.thery.poc.dto.DataTransfertObject;

/**
 * Tools for querydsl queries.
 *
 * @author Thery
 *
 */
public final class QuerydslSpringUtils {

  /**
   * Private constructor.
   */
  private QuerydslSpringUtils() {
    super();
  }

  /**
   * Paginate and sort the query using reflexion. INFO : The Dto attributes MUST be in the same
   * order as in his constructor.
   *
   * @param <T> the entity type
   * @param pQuery the query
   * @param pConstr the expression constructor
   * @param pPager the pager
   */
  public static <T extends DataTransfertObject> void paginateAndSort(JPQLQuery pQuery,
      Pageable pPager, ConstructorExpression<T> pConstr) {
    if (pPager != null) {
      applyPagination(pQuery, pPager);
      applySort(pQuery, pConstr.getType(), pPager.getSort(), pConstr.getArgs());
    }
  }

  /**
   * Apply pagination to the given query.
   *
   * @param pQuery {@link JPQLQuery}
   * @param pPageable {@link Pageable}
   * @return {@link JPQLQuery}
   */
  public static JPQLQuery applyPagination(JPQLQuery pQuery, Pageable pPageable) {
    if (pPageable == null) {
      return pQuery;
    }

    pQuery.offset(pPageable.getOffset());
    pQuery.limit(pPageable.getPageSize());

    return pQuery;
  }

  /**
   * Apply sorting to a query using reflexion, it gets the position of the property in the class and
   * match it to the position in the expression list.
   *
   * @param <T> the Dto type
   * @param pQuery {@link JPQLQuery}
   * @param pClass the class
   * @param pSort {@link Sort}
   * @param pExprList the expression list
   * @return the query
   */
  public static <T extends DataTransfertObject> JPQLQuery applySort(JPQLQuery pQuery,
      Class<T> pClass, Sort pSort, List<Expression<?>> pExprList) {

    if (pSort != null) {
      for (Order order : pSort) {
        ComparableExpressionBase<?> path =
            (ComparableExpressionBase<?>) pExprList.get(getPosition(pClass, order.getProperty()));
        pQuery.orderBy(order.isAscending() ? path.asc() : path.desc());
      }
    }
    return pQuery;
  }

  /**
   * Retrieve the position of the property in the class (using reflexion).
   *
   * @param pClass the class
   * @param pProperty the property string
   * @return the position
   */
  private static Integer getPosition(Class<?> pClass, String pProperty) {
    List<Field> fieldList = Arrays.asList(pClass.getDeclaredFields());

    int i = 0;
    if (!CollectionUtils.isEmpty(fieldList)) {
      for (Field field : fieldList) {
        if (field.getName().equals(pProperty)) {
          return i;
        }

        if (!Modifier.isStatic(field.getModifiers())) {
          i++;
        }
      }

    }
    return 0;
  }

  /**
   * Creates a new pager.
   *
   * @param page the current page
   * @param size the size of the page
   * @param pSortField the sort field
   * @param direction {@link Direction}
   * @return {@link Pageable}
   */
  public static Pageable createPager(int page, int size, String pSortField, Direction direction) {
    Sort sort = new Sort(new Sort.Order(direction, pSortField));
    return new PageRequest(page, size, sort);
  }

  /**
   * Add a sort to an existing pager.
   *
   * @param pPager {@link Pageable}
   * @param pField the field to sort
   * @param pDirection {@link Direction}
   * @return {@link Pageable}
   */
  public static Pageable addSort(Pageable pPager, String pField, Direction pDirection) {
    Iterator<Order> orderIterator = pPager.getSort().iterator();
    List<Order> orderList = new ArrayList<Order>();

    // Iterate on sort field list
    while (orderIterator.hasNext()) {
      orderList.add(orderIterator.next());
    }
    // Add given order
    Order order = new Sort.Order(pDirection, pField);
    if (!orderList.contains(order)) {
      orderList.add(order);
    }

    return new PageRequest(pPager.getPageNumber(), pPager.getPageSize(), new Sort(orderList));
  }
}
