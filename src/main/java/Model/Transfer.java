package Model;

import lombok.*;

import java.beans.ConstructorProperties;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    private int weight;
    private int cost;
}
