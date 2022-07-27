package uz.oliymahad.oliymahadquroncourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.oliymahad.oliymahadquroncourse.entity.Section;
import java.util.Optional;



@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findByName(String  name);

}
