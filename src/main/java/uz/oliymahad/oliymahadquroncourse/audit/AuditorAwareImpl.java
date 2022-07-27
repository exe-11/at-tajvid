package uz.oliymahad.oliymahadquroncourse.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;



public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor()
    {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(auth) && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal()))
            return  Optional.of(auth.getPrincipal().toString());
        return Optional.of("anonymousUser");
    }
}
