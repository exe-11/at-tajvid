package uz.oliymahad.oliymahadquroncourse.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.oliymahad.oliymahadquroncourse.audit.Auditable;
import uz.oliymahad.oliymahadquroncourse.entity.enums.Gender;
import uz.oliymahad.oliymahadquroncourse.entity.enums.GroupStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "groups")
public class Group extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int membersCount;

    @Enumerated(EnumType.ORDINAL)
    private GroupStatus groupStatus = GroupStatus.UPCOMING;

    @Column(nullable = false)
    private Date startDate = new Date();

    @ManyToOne
    private Course course;


    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users;
}
