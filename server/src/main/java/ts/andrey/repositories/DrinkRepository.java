package ts.andrey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ts.andrey.common.data.entity.Drink;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Integer> {
}
