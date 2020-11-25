package engine.Dto;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", updatable = false, insertable = false)
    private Quiz quiz;


    @NotNull
    @NonNull
    private String option;
}
