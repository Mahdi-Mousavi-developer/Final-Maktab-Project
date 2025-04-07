package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.entity.Dto.WebappUserRequestDto;
import ir.maktabsharif.finalproject.entity.User;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;


import java.util.List;

public interface UserService {

    User createUser(WebappUserRequestDto webappUserRequestDto);

    User updateUser(Long id, WebappUserRequestDto webappUserRequestDto) throws EntityNotFoundException;

    User updateWithoutChangingPassword(Long id, WebappUserRequestDto webappUserRequestDto) throws EntityNotFoundException;

    User approveUser(Long id) throws EntityNotFoundException;


    List<User> findAllWebappUsers();

    List<User> findAllUserPendingRegistrationStatus();


    void deleteUser(Long id) throws EntityNotFoundException;


    //starting phase\3 method
    User findUserById(Long id) throws EntityNotFoundException;

    List<User> findUserByNationalCode(String nationalCode) throws EntityNotFoundException;

    List<User> findUserByUsernameForSearch(String username);

    List<User> findUserByLastName(String lastname);

    List<User> findUserByLastFirstName(String firstname);

    List<User> findUserByRole(String role) throws EntityNotFoundException;

    List<User> findStudentsNotInCourse(Long courseId);


    //start second part method
}
