package uz.oliymahad.oliymahadquroncourse.payload.response.section;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SectionResponse {

    @NotNull
    private Long id;

    @NotBlank
    private String sectionName;

    @NotNull
    private List<Content> content;

}