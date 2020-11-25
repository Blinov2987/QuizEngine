package engine.repository;

import engine.model.RqQuiz;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RqQuizPagingRepository extends PagingAndSortingRepository<RqQuiz, Integer> {
}
