package ts.andrey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ts.andrey.entity.Syrup;

@Repository
public interface SyrupRepository extends JpaRepository<Syrup, Integer> {
}
