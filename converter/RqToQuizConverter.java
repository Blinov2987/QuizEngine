package engine.converter;

import engine.Dto.QuestionAnswer;
import engine.Dto.QuestionOption;
import engine.Dto.Quiz;
import engine.model.RqQuiz;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@NoArgsConstructor
public class RqToQuizConverter {

    public Quiz convert(RqQuiz rqQuiz)
    {
        return Quiz.builder()
                .id(rqQuiz.getId())
                .title(rqQuiz.getTitle())
                .text(rqQuiz.getText())
                .options(getOptions(rqQuiz.getOptions()))
                .answer(getAnswer(rqQuiz.getAnswer()))
                .build();
    }

    private List<QuestionOption> getOptions(List<String> options)
    {
        if (options == null) {
            return Collections.emptyList();
        }
        List<QuestionOption> questionOptions = new ArrayList<>();
        options.forEach(option -> questionOptions.add(new QuestionOption(option)));
        return questionOptions;
    }

    private List<QuestionAnswer> getAnswer(List<Integer> answers) {
        if (answers == null) {
            return Collections.emptyList();
        }
        List<QuestionAnswer> questionAnswers = new ArrayList<>();
        answers.forEach(answer -> questionAnswers.add(new QuestionAnswer(answer)));
        return questionAnswers;
    }
}
