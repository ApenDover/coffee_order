package ts.andrey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ts.andrey.entity.CafeOrder;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<CafeOrder, Integer> {

    @Modifying
    @Query("UPDATE CafeOrder SET status = :status, dateReady = :date WHERE id = :id")
    void update(
            @Param("status") boolean status,
            @Param("date") OffsetDateTime date,
            @Param("id") int id);

    @Query(value = "select o from CafeOrder o where o.date > CURRENT_DATE")
    List<CafeOrder> findAllByDateAfter();

}
