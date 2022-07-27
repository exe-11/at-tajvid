package uz.oliymahad.oliymahadquroncourse.payload.response.section;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SectionPermission {

    private boolean visibility;

    private boolean update;

    private boolean delete;

    private boolean info;
}
