package dev.SpringBootAPI.ECommerce.repositories;

import dev.SpringBootAPI.ECommerce.models.user.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {

}