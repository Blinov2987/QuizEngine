package engine.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.lang.NonNull;


import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@Entity
public class RqQuiz {

    @Transient
    public static int nextId;

    @Id
    private Integer id;

    @NotNull
    @NotEmpty
    @Valid
    private String title;

    @NonNull
    @NotEmpty
    @Valid
    private String text;

    @ElementCollection
    @NotEmpty
    private @Size(min = 2, message = "min option is 2") List<String> options;

    @ElementCollection
    @Size(max = 3)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;

    public RqQuiz() {
        this.id = ++nextId;
    }
}
