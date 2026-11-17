package org.codewiththamsanqa.springboot_db.services;

import org.codewiththamsanqa.springboot_db.exceptions.ResourceNotFoundException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.codewiththamsanqa.springboot_db.dto.StudentRequest;
import org.codewiththamsanqa.springboot_db.dto.StudentResponse;
import org.codewiththamsanqa.springboot_db.entity.*;
import org.codewiththamsanqa.springboot_db.exceptions.DuplicateResourceException;
import org.codewiththamsanqa.springboot_db.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import static org.codewiththamsanqa.springboot_db.dto.StudentResponse.mapToResponse;

@Service
public class StudentService {
    final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    //create
    @Transactional
    public StudentResponse createStudent(StudentRequest request){
        if(studentRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException(
                    "A student with this email already exists"
            );

            }

        Students student = new Students();
        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setEmail(request.email());

        Students savedStudent = studentRepository.save(student);

        return mapToResponse(savedStudent);
    }

    //read all

    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(StudentResponse::mapToResponse)
                .toList();
    }

    private  Students findStudentOrThrow(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id" + id));
    }

    private StudentResponse mapToResponse(Students student) {

        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );
    }
    //update
    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest request){
        Students student = findStudentOrThrow(id);

        boolean emailUsedByAnotherStudent =
                studentRepository.existByEmailAndIdNot(
                        request.email(),id
                );

            if(emailUsedByAnotherStudent){
                throw new DuplicateResourceException(
                        "Another student is already using this email"
                );
            }

            student.setFirstName(request.firstName());
            student.setLastName(request.lastName());
            student.setEmail(request.email());

            Students updatedStudent = studentRepository.save(student);

            return mapToResponse(updatedStudent);
    }

    //delete
    @Transactional
    public void deleteStudent(Long id){
        Students student = findStudentOrThrow(id);

        studentRepository.delete(student);
    }



}
