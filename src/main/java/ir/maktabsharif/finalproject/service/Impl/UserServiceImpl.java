package ir.maktabsharif.finalproject.service.Impl;

import ir.maktabsharif.finalproject.entity.Dto.WebappUserRequestDto;
import ir.maktabsharif.finalproject.entity.RegistrationStatus;
import ir.maktabsharif.finalproject.entity.Role;
import ir.maktabsharif.finalproject.entity.User;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.repository.UserRepository;
import ir.maktabsharif.finalproject.service.UserService;
import ir.maktabsharif.finalproject.service.password.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordService passwordService;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(PasswordService passwordService, UserRepository userRepository) {
        this.passwordService = passwordService;
        this.userRepository = userRepository;
    }


    //Helper method
    private User mapDtoToEntity(WebappUserRequestDto dto, User entity) {
        entity.setUsername(dto.getUsername());
        entity.setPassword(passwordService.hashPassword(dto.getPassword()));
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDob(dto.getDob());
        entity.setNationalCode(dto.getNationalCode());
        entity.setRole(dto.getRole());
        return entity;
    }

    @Override
    public User createUser(WebappUserRequestDto webappUserRequestDto) {
        User user = new User();
        return userRepository.save(this.mapDtoToEntity(webappUserRequestDto, user));
    }

    @Override
    public User updateUser(Long id, WebappUserRequestDto webappUserRequestDto) throws EntityNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userRepository.save(mapDtoToEntity(webappUserRequestDto, user));
    }

    @Override
    public User updateWithoutChangingPassword(Long id, WebappUserRequestDto webappUserRequestDto) throws EntityNotFoundException {
        User user = findUserById(id);
        user.setFirstName(webappUserRequestDto.getFirstName());
        user.setLastName(webappUserRequestDto.getLastName());
        user.setUsername(webappUserRequestDto.getUsername());
        user.setNationalCode(webappUserRequestDto.getNationalCode());
        user.setRole(webappUserRequestDto.getRole());

        return userRepository.save(user);
    }

    @Override
    public List<User> findAllWebappUsers() {
        return userRepository.findAll();
    }


    @Override
    public List<User> findAllUserPendingRegistrationStatus() {
        return userRepository.findByRegistrationStatus(RegistrationStatus.PENDING);
    }

    @Override
    public User approveUser(Long id) throws EntityNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setRegistrationStatus(RegistrationStatus.APPROVAL);
        return userRepository.save(user);
    }


    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }


    //starting phase\3 method
    @Override
    public User findUserById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public List<User> findUserByUsernameForSearch(String username) {
        return userRepository.findByUsernameForSearch(username);
    }

    @Override
    public List<User> findUserByLastName(String lastname) {
        return userRepository.findByLastName(lastname);
    }

    @Override
    public List<User> findUserByLastFirstName(String firstname) {
        return userRepository.findByFirstName(firstname);
    }

    @Override
    public List<User> findUserByNationalCode(String nationalCode) throws EntityNotFoundException {
        return userRepository.findWebappUserByNationalCode(nationalCode);

    }


    @Override
    public List<User> findUserByRole(String role) throws EntityNotFoundException {
        List<User> userList = List.of();
        if (role.equalsIgnoreCase("STUDENT")) {
            userList = userRepository.findWebappUserByRole(Role.STUDENT);
        } else {
            userList = userRepository.findWebappUserByRole(Role.TEACHER);
        }
        if (userList.isEmpty()) {
            throw new EntityNotFoundException("there is no user");
        }
        return userList;
    }


    public List<User> findStudentsNotInCourse(Long courseId) {
        return userRepository.findStudentsNotInCourse(courseId);
    }

}

//    // check kardan username va password
//    @Override
//    public Boolean authenticate(String username, String rawPassword) {
//        WebappUser user = webappUserRepository.findWebappUserByUsername(username);
//        if (user == null) {
//            return false;
//        }
//        return passwordService.checkPassword(rawPassword, user.getPassword()); // بررسی تطابق پسوورد
//    }
