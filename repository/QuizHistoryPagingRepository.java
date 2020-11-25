package engine.repository;

import engine.Dto.QuizHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizHistoryPagingRepository extends PagingAndSortingRepository<QuizHistory, Integer> {

    Page<QuizHistory> findByCompletedByUser(Integer completedByUser, Pageable paging);
}
