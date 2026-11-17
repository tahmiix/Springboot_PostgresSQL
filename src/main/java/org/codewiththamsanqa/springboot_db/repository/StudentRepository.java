package org.codewiththamsanqa.springboot_db.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.codewiththamsanqa.springboot_db.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students,Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Students> findByEmail(String email);



    boolean existByEmailAndIdNot(@NotBlank(message = "Email is required") @Email(message = "Enter a valid email address") @Size(max = 255, message = "Email must not exceed 255 characters") String email, Long id);
}
