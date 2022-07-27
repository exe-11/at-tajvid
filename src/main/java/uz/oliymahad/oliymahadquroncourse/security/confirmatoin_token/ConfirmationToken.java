package uz.oliymahad.oliymahadquroncourse.security.confirmatoin_token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.oliymahadquroncourse.entity.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token = UUID.randomUUID().toString();

    @Column(nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private Date expiryDate;

    private Date confirmationDate;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(nullable = false,
            name = "user_id")
    private User user;

    public ConfirmationToken(Date creationDate, Date expiryDate, User user) {
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.user = user;
    }
}
