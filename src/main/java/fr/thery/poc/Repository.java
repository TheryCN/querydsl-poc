package fr.thery.poc;


import java.io.Serializable;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Spring DATA JPA Repository : http://docs.spring.io/spring-data/jpa/docs/current/reference/html/.
 *
 * @author Thery
 *
 * @param <T> Entity
 * @param <ID> Entity Id
 */
public interface Repository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

}
