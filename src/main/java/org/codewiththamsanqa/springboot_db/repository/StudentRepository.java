package org.codewiththamsanqa.springboot_db.repository;

import org.codewiththamsanqa.springboot_db.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students,Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Students> findByEmail(String email);



}
