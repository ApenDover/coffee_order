package ts.andrey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ts.andrey.entity.Ordering;

import java.util.Date;

@Repository
public interface OrderRepository extends JpaRepository<Ordering, Integer> {

    @Modifying
    @Query("UPDATE Ordering SET status = :status, dateReady = :date WHERE id = :id")
    void update(
            @Param("status") boolean status,
            @Param("date") Date date,
            @Param("id") int id);

}
