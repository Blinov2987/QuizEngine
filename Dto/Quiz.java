package engine.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quiz {

    @Id
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotNull
    private String author;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", nullable = false)
    @OrderColumn(name = "option_position", nullable = false)
    private @Size(min = 2, message = "min option is 2") List<QuestionOption> options = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", nullable = false)
    @OrderColumn(name = "answer_position", nullable = false)
    private List<QuestionAnswer> answer = new ArrayList<>();

}
