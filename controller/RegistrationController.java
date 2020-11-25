package engine.controller;

import engine.Dto.User;
import engine.converter.UserConverter;
import engine.model.QuizUser;
import engine.repository.UserListRepository;
import engine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.module.FindException;

@RestController
@RequiredArgsConstructor
public class RegistrationController {


    @Autowired
    private final UserConverter converter;

    @Autowired
    private final UserService userService;

    @PostMapping(path = "/api/register")
    public ResponseEntity<QuizUser>  createRegister(@Valid @RequestBody QuizUser quizUser) {

        User user = converter.convertToUser(quizUser);

        if (userService.saveUser(user)) {
            return new ResponseEntity<QuizUser>(HttpStatus.OK);
        }

        return new ResponseEntity<QuizUser>(HttpStatus.BAD_REQUEST);
    }
}
