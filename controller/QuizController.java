package engine.controller;

import engine.Dto.Quiz;
import engine.Dto.QuizHistory;
import engine.Dto.User;
import engine.converter.QuizToRqConverter;
import engine.converter.RqToQuizConverter;
import engine.model.RqQuiz;
import engine.model.RequestAnswer;
import engine.model.SuccessAnswer;
import engine.repository.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@RestController
@RequiredArgsConstructor
public class QuizController {

    @Autowired
    private final RqToQuizConverter toQuizConverter;

    @Autowired
    private final QuizToRqConverter toRqConverter;

    @Autowired
    private final QuizRepository repository;

    @Autowired
    private final RqQuizRepository rqQuizRepository;

    @Autowired
    private final RqQuizPagingRepository rqQuizPagingRepository;


    @Autowired
    private final QuizHistoryPagingRepository historyPagingRepository;

    @Autowired
    private final QuizHistoryRepository historyRepository;

    @Autowired
    private final UserListRepository userListRepository;



    public void saveAnswer(Integer quizId) {
        String userName = getUserName();
        Integer currentUserId = userListRepository.findByUsername(userName).getId();
        Instant instant = Instant.now();

        QuizHistory history = QuizHistory.builder()
                .completedByUser(currentUserId)
                .completedAt(instant.toString())
                .id(quizId)
                .build();

        historyRepository.save(history);
    }

    private static RuntimeException notFound() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
    }

    @Secured(value = "ROLE_USER")
    @GetMapping(path = "/api/quizzes/{id}")
    public RqQuiz getQuiz(@PathVariable Integer id) {
        return getQuizById(id);
    }

    @Secured(value = "ROLE_USER")
    @PostMapping(path = "/api/quizzes/{id}/solve")
    public SuccessAnswer postAnswer(@PathVariable int id, @RequestBody RequestAnswer answer) {
        RqQuiz current = getQuizById(id);

        SuccessAnswer successAnswer;
        if (isEmpty(current.getAnswer()) && isEmpty(answer.getAnswer())) {
            successAnswer = new SuccessAnswer(true, "Congratulations, you're right!");
            saveAnswer(id);
        } else if (isEmpty(current.getAnswer()) || isEmpty(answer.getAnswer())) {
            successAnswer = new SuccessAnswer(false, "Wrong answer! Please, try again.");
        } else if (current.getAnswer().equals(answer.getAnswer())) {
            saveAnswer(id);
            successAnswer = new SuccessAnswer(true, "Congratulations, you're right!");
        } else {
            successAnswer = new SuccessAnswer(false, "Wrong answer! Please, try again.");
        }
        return successAnswer;
    }

    private RqQuiz getQuizById(@PathVariable Integer id) {
        Quiz quiz = repository.findById(id).orElseThrow(QuizController::notFound);
        return toRqConverter.convert(quiz);
    }

    private String getUserName() {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return user.getUsername();
    }

    @Secured(value = "ROLE_USER")
    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public RqQuiz createQuiz(@Valid @RequestBody RqQuiz newRqQuiz) {
        rqQuizRepository.save(newRqQuiz);
        Quiz quiz = toQuizConverter.convert(newRqQuiz);
        quiz.setAuthor(getUserName());
        repository.save(quiz);
        return newRqQuiz;
    }

    @Secured(value = "ROLE_USER")
    @DeleteMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable int id) {
        Quiz current = repository.findById(id).orElse(null);
        if (current == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!current.getAuthor().equals(getUserName())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        repository.delete(current);
        rqQuizRepository.delete(rqQuizRepository.findById(id).get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured(value = "ROLE_USER")
    @GetMapping(path = "/api/quizzes")
    public Page<RqQuiz> getAllQuizzes(@RequestParam("page") Integer id) {
        PageRequest pageable = PageRequest.of(id, 10);
        Page<RqQuiz> page = rqQuizPagingRepository.findAll(pageable);
        return page;
    }

    @Secured(value = "ROLE_USER")
    @GetMapping(path = "/api/quizzes/completed")
    public Page<QuizHistory> getQuizHistory(@RequestParam("page") Integer id) {
        PageRequest pageable = PageRequest.of(id, 10, Sort.by("completedAt").descending());
        Page<QuizHistory> page = historyPagingRepository.findByCompletedByUser(
                userListRepository.findByUsername(
                        getUserName()
                ).getId(),
                pageable
        );
        return page;
    }
}