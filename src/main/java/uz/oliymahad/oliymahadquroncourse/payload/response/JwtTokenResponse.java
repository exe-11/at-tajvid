package uz.oliymahad.oliymahadquroncourse.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.entity.enums.Language;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenResponse {

    private long id;

    private int roles;

    private String username;

    private Language language;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("access_token")
    private String accessToken;
}