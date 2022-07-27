package uz.oliymahad.oliymahadquroncourse.payload.request.section;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SectionRequest {

    @NotBlank
    private String sectionName;

    @JsonProperty("content")
    private List<RolePermission> rolePermissionList;

}
