package dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetNewsDto<T> {
    private List<T> content = List.of();
    private int notReady;
    private int numberOfElements;
    private int ready;

}
