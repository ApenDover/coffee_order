package ts.andrey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ts.andrey.common.data.entity.Ordering;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Ordering, Integer> {

    @Modifying
    @Query("UPDATE Ordering SET status = :status, dateReady = :date WHERE id = :id")
    void update(
            @Param("status") boolean status,
            @Param("date") LocalDateTime date,
            @Param("id") int id);

    @Query(value = "select o from Ordering o where o.date > CURRENT_DATE")
    List<Ordering> findAllByDateAfter();

}
