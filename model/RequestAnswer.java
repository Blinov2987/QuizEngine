package engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
public class RequestAnswer {

    @Size(max = 3)
    List<Integer> answer;

}
