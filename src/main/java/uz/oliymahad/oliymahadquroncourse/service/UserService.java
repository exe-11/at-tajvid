package uz.oliymahad.oliymahadquroncourse.service;


import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.annotation.phone_num_constraint.RegistrationValidator;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.entity.enums.Language;
import uz.oliymahad.oliymahadquroncourse.entity.enums.Role;
import uz.oliymahad.oliymahadquroncourse.entity.enums.UserStatus;
import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;
import uz.oliymahad.oliymahadquroncourse.exception.UserNotFoundException;
import uz.oliymahad.oliymahadquroncourse.payload.APIResponse;
import uz.oliymahad.oliymahadquroncourse.payload.request.user.RoleUpdateRequest;
import uz.oliymahad.oliymahadquroncourse.payload.request.user.StatusRequest;
import uz.oliymahad.oliymahadquroncourse.payload.request.user.UserSigningRequest;
import uz.oliymahad.oliymahadquroncourse.payload.request.user.UserUpdateRequest;
import uz.oliymahad.oliymahadquroncourse.payload.response.JwtTokenResponse;
import uz.oliymahad.oliymahadquroncourse.payload.response.user.RegistrationDetailsResponse;
import uz.oliymahad.oliymahadquroncourse.payload.response.user.UserDataResponse;
import uz.oliymahad.oliymahadquroncourse.repository.UserRepository;
import uz.oliymahad.oliymahadquroncourse.service.core.CRUDService;
import uz.oliymahad.oliymahadquroncourse.service.core.PageProvider;
import uz.oliymahad.oliymahadquroncourse.service.core.ResourcePage;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements CRUDService<
        Long,
        APIResponse,
        UserSigningRequest,
        UserUpdateRequest > , PageProvider<APIResponse>
{

    public static final String USER = "User";

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final RegistrationValidator registrationValidator;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, RegistrationValidator registrationValidator) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.registrationValidator = registrationValidator;
    }

    @Override
    public APIResponse create(UserSigningRequest userRegistrationRequest) {
        final String registeredBy = userRegistrationRequest.getPhoneNumberOrEmail();
        final User user = userRepository.save(modelMapper.map(userRegistrationRequest, User.class));

        if (registrationValidator.isEmail(registeredBy)){
            user.setEmail(registeredBy);
        }

        if (registrationValidator.isPhoneNumber(registeredBy)){
            user.setPhoneNumber(registeredBy);
        }

        return APIResponse.success(user);
    }

    @Override
    public APIResponse get(Long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> {
            throw UserNotFoundException.of(String.valueOf(id));
        });
        return APIResponse.success(user);
    }

    @Override
    public APIResponse modify(Long id,final UserUpdateRequest userUpdateRequest) {
        final User user = userRepository.findById(id).orElseThrow(() -> {
            throw UserNotFoundException.of(String.valueOf(id));
        });
        modelMapper.map(userUpdateRequest, user);
        return APIResponse.success(userRepository.save(user));
    }

    @Override
    public APIResponse delete(final Long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> {
            throw UserNotFoundException.of(String.valueOf(id));
        });
        userRepository.delete(user);
        return APIResponse.success(HttpStatus.OK.name());
    }

    public APIResponse getUserByEmail(String email) {
        return APIResponse.success(
                modelMapper.map(userRepository
                                .findByEmailAndStatusIsNot(email, UserStatus.DELETED)
                                .orElseThrow(() -> UserNotFoundException.of(email)),
                        JwtTokenResponse.class));
    }


    public APIResponse updateUserStatus(final StatusRequest request) {
        final User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> DataNotFoundException.of(USER, request.getUserId())
        );

        user.setUserStatus(UserStatus.valueOf(request.getStatus()));
        userRepository.save(user);
        return APIResponse.success(HttpStatus.OK.name());
    }

    public APIResponse updateUserRole(final RoleUpdateRequest request) {
        final User user = userRepository.findUserByIdAndStatus(request.getId(), -1).orElseThrow(
                () -> DataNotFoundException.of(USER, request.getId())
        );
        user.setRoles(request.getRole());
        return APIResponse.success(HttpStatus.OK.name());
    }


    public APIResponse updateLanguage(Long userId, String lang) {
        final User user = userRepository.findById(userId).orElseThrow(() -> {
            throw UserNotFoundException.of(String.valueOf(userId));
        });
        user.setLanguage(Language.valueOf(lang.toUpperCase()));
        userRepository.save(user);
        return APIResponse.success(HttpStatus.OK.value());
    }

    public static List<String> getRoles(int roleValue) {
        final List<String> roleList = new ArrayList<>();
        for (Role role : Role.values()) {
            if ((roleValue & role.flag) == role.flag)
                roleList.add(role.name());
        }
        return roleList;
    }



    @Override
    public APIResponse pageOf(Pageable pageable) {
        final Page<User> userPage = userRepository.findAll(pageable);
        final ResourcePage<UserDataResponse> response = new ResourcePage<>();
        modelMapper.map(userPage, response);
        return APIResponse.success(response);
    }

    public APIResponse getUserDetails (long userId) {
        final User user = userRepository.findById(userId).orElseThrow(() -> DataNotFoundException.of(USER, userId));
        return APIResponse.success(modelMapper.map(user.getRegistrationDetails(), RegistrationDetailsResponse.class));
    }

}
