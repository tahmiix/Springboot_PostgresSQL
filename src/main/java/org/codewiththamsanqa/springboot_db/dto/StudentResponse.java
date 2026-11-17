package org.codewiththamsanqa.springboot_db.dto;

import org.codewiththamsanqa.springboot_db.entity.Students;

public record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        String email
) {
    public static StudentResponse mapToResponse(Students student) {
        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()

        );
    }

}
