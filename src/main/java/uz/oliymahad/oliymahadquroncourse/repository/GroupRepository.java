package uz.oliymahad.oliymahadquroncourse.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.oliymahad.oliymahadquroncourse.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
