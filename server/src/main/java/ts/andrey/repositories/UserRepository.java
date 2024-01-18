package ts.andrey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ts.andrey.common.data.entity.BaristaUser;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<BaristaUser, Integer> {

    List<BaristaUser> findBaristaUserByName(String userName);

}
