package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepository extends JpaRepository<User, String> {
    @Query(
        value = "SELECT u.* FROM users u " +
                "JOIN user_interested_event ue ON u.user_id = ue.user_id " +
                "WHERE ue.event_id = :eventId AND u.user_allow_email_notification = true",
        nativeQuery = true
    )
    List<User> findInterestedUsersWithNotificationsEnabled(@Param("eventId") String eventId);
}