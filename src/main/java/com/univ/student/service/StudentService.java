package com.univ.student.service;

import com.univ.student.domain.entity.StudentEntity;
import com.univ.student.domain.model.StudentDto;
import com.univ.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final ModelMapper mapper;
    private final StudentRepository studentRepository;

    public StudentDto createStudent(StudentDto studentDto) {
        StudentEntity studentEntity = studentRepository.save(convertDtoToEntity(studentDto));

         return convertEntityToDto(studentEntity);
    }

    private StudentEntity convertDtoToEntity(StudentDto studentDto) {
        StudentEntity studentEntityToSave = //mapper.map(studentDto, StudentEntity.class);
                new StudentEntity(studentDto.getId(),studentDto.getFirstName(),studentDto.getLastName(),
                        studentDto.getDob(), studentDto.getAdmissionDate(), studentDto.getRegistrationId(),
                        studentDto.getDepartmentName(), studentDto.getCourseName());
        return studentEntityToSave;
    }

    private StudentDto convertEntityToDto(StudentEntity entity) {
        StudentDto studentDto = //mapper.map(entity, StudentDto.class);
                StudentDto.builder().id(entity.getId()).admissionDate(entity.getAdmissionDate())
                        .departmentName(entity.getDepartmentName()).courseName(entity.getCourseName())
                        .dob(entity.getDob()).firstName(entity.getFirstName())
                        .lastName(entity.getLastName()).registrationId(entity.getRegistrationId())
                        .build();
        return studentDto;
    }

    public List<StudentDto> getStudentList() {
        return studentRepository.findAll().stream().map(record -> convertEntityToDto(record)).collect(Collectors.toList());
    }

    public StudentDto getStudent(Long studentId) {
        Optional<StudentEntity> studentEntityOptional = studentRepository.findById(studentId);
        if(studentEntityOptional.isPresent()) {
            return convertEntityToDto(studentEntityOptional.get());
        }
        return null;
    }

    public List<StudentDto> getStudentByDepartmentName(String departmentName) {
        return studentRepository.findByDepartmentName(departmentName).stream().map(record -> convertEntityToDto(record)).collect(Collectors.toList());
    }
}
