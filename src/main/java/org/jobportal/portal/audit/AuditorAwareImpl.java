package org.jobportal.portal.audit;

import org.jobportal.portal.util.ApplicationUtility;
import org.springframework.data.domain.AuditorAware;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditoAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(ApplicationUtility.getLoggedInUser());
    }
}
