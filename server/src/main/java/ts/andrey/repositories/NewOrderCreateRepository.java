package ts.andrey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ts.andrey.entity.NewOrderCreate;

public interface NewOrderCreateRepository extends JpaRepository<NewOrderCreate, Integer> {
}
