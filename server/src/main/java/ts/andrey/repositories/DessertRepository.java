package ts.andrey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ts.andrey.entity.Dessert;

@Repository
public interface DessertRepository extends JpaRepository<Dessert, Integer> {
}
