package uz.oliymahad.oliymahadquroncourse.service;


import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.entity.RegistrationDetails;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;
import uz.oliymahad.oliymahadquroncourse.payload.APIResponse;
import uz.oliymahad.oliymahadquroncourse.payload.request.register_details.RegistrationDetailsCreationRequest;
import uz.oliymahad.oliymahadquroncourse.payload.request.register_details.RegistrationDetailsUpdateRequest;
import uz.oliymahad.oliymahadquroncourse.payload.response.user.RegistrationDetailsResponse;
import uz.oliymahad.oliymahadquroncourse.repository.RegistrationDetailsRepository;
import uz.oliymahad.oliymahadquroncourse.repository.UserRepository;
import uz.oliymahad.oliymahadquroncourse.service.core.CRUDService;

import javax.persistence.PersistenceException;
import javax.xml.crypto.Data;

import static uz.oliymahad.oliymahadquroncourse.service.UserService.USER;

@AllArgsConstructor
@Service
public class RegistrationDetailsService implements CRUDService<Long, APIResponse, RegistrationDetailsCreationRequest, RegistrationDetailsUpdateRequest> {

    private final static Logger log = LoggerFactory.getLogger(RegistrationDetailsService.class);

    public final static String REGISTRATION_DETAILS = "Registration details";

    private final RegistrationDetailsRepository registrationDetailsRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    @Override
    public APIResponse create(RegistrationDetailsCreationRequest request) {
        final User user = userRepository.findById(request.getUserId()).orElseThrow(() -> DataNotFoundException.of(USER, request.getUserId()));
        RegistrationDetails details = modelMapper.map(request, RegistrationDetails.class);
        details.setUser(user);
        try {
            details = registrationDetailsRepository.save(details);
        }catch (Exception exception){
            throw new PersistenceException();
        }
        return APIResponse.success(modelMapper.map(details, RegistrationDetailsResponse.class));
    }

    @Override
    public APIResponse get(Long id) {
        final RegistrationDetails details = registrationDetailsRepository.findById(id).orElseThrow(() -> DataNotFoundException.of(REGISTRATION_DETAILS, id));
        return APIResponse.success(modelMapper.map(details, RegistrationDetailsResponse.class));
    }

    @Override
    public APIResponse modify(Long id, RegistrationDetailsUpdateRequest request) {
        final RegistrationDetails details = registrationDetailsRepository.findById(id).orElseThrow(() -> DataNotFoundException.of(REGISTRATION_DETAILS, id));
        modelMapper.map(details, request);
        return APIResponse.success(modelMapper.map(details, RegistrationDetailsResponse.class));
    }

    @Override
    public APIResponse delete(Long id) {
        final RegistrationDetails details = registrationDetailsRepository.findById(id).orElseThrow(() -> DataNotFoundException.of(REGISTRATION_DETAILS, id));
        registrationDetailsRepository.delete(details);
        return APIResponse.success(HttpStatus.OK.name());
    }
}
