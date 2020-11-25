package engine.converter;

import engine.Dto.User;
import engine.model.QuizUser;
import engine.security.Role;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
public class UserConverter {

    public User convertToUser(QuizUser quizUser) {
        return User.builder()
                .username(quizUser.getEmail())
                .password(quizUser.getPassword())
                .build();
    }
}
