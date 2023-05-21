package ts.andrey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ts.andrey.common.data.entity.Milk;

@Repository
public interface MilkRepository extends JpaRepository<Milk, Integer> {
}
