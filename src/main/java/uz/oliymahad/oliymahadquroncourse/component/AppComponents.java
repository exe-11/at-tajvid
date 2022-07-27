package uz.oliymahad.oliymahadquroncourse.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.oliymahad.oliymahadquroncourse.entity.Queue;
import uz.oliymahad.oliymahadquroncourse.payload.response.queue.QueueResponse;

@Component
public class AppComponents {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setDeepCopyEnabled(true);

        TypeMap<Queue, QueueResponse> queueTypeMap = modelMapper.createTypeMap(Queue.class, QueueResponse.class);
        queueTypeMap.addMappings(mapper -> {
            mapper.map(scr -> scr.getCourse().getName(), QueueResponse::setCourseName);
            mapper.map(scr -> scr.getUser().getId(), QueueResponse::setUserId);
            mapper.map(scr -> scr.getUser().getEmail(), QueueResponse::setEmail);
            mapper.map(scr -> scr.getUser().getPhoneNumber(), QueueResponse::setPhoneNumber);
            mapper.map(scr -> scr.getUser().getRegistrationDetails().getFirstName(), QueueResponse::setFirstName);
            mapper.map(scr -> scr.getUser().getRegistrationDetails().getLastName(), QueueResponse::setLastName);
            mapper.map(scr -> scr.getUser().getRegistrationDetails().getMiddleName(), QueueResponse::setMiddleName);
            mapper.map(scr -> scr.getUser().getRegistrationDetails().getBirthDate(), QueueResponse::setBirthDate);
            mapper.map(scr -> scr.getUser().getRegistrationDetails().getPassport(), QueueResponse::setPassportSerial);
            mapper.map(scr -> scr.getUser().getRegistrationDetails().getGender(), QueueResponse::setGender);
        });


        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
