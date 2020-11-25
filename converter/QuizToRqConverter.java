package engine.converter;

import engine.Dto.QuestionAnswer;
import engine.Dto.QuestionOption;
import engine.Dto.Quiz;
import engine.model.RqQuiz;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizToRqConverter {
    public RqQuiz convert(Quiz quiz)
    {
        return RqQuiz.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .text(quiz.getText())
                .options(getOptions(quiz.getOptions()))
                .answer(getAnswer(quiz.getAnswer()))
                .build();
    }

    private List<String> getOptions(List<QuestionOption> questionOptions)
    {
        if (questionOptions == null) {
            return Collections.emptyList();
        }
        List<String> options = new ArrayList<>();
        questionOptions.forEach(questionOption -> options.add(questionOption.getOption()));
        return options;
    }

    private List<Integer> getAnswer(List<QuestionAnswer> questionAnswerSet) {
        if (questionAnswerSet == null) {
            return Collections.emptyList();
        }
        List<Integer> answers = new ArrayList<>();
        questionAnswerSet.forEach(questionAnswer -> answers.add(questionAnswer.getAnswer()));
        return answers;
    }

    public List<RqQuiz> convertAll(List<Quiz> quizzes) {
        List<RqQuiz> rqQuizs = new ArrayList<>();
        quizzes.forEach(quiz -> rqQuizs.add(convert(quiz)));
        return rqQuizs;
    }
}
