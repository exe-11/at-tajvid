package uz.oliymahad.oliymahadquroncourse.payload.response.section;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Content {

    private int ordinal;

    @NotBlank
    private String roleName;

    @NotNull
    private SectionPermission permissions;

}
