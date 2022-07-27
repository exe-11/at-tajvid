package uz.oliymahad.oliymahadquroncourse.payload.response.section;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminSection {

    private boolean delete;

    private boolean info;

    private boolean update;

    private List<String> headers;

    private Object body;
}
