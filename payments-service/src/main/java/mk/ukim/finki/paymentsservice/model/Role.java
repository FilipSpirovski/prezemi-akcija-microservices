package mk.ukim.finki.paymentsservice.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_FORUM_SERVICE,
    ROLE_INITIATIVES_SERVICE,
    ROLE_PAYMENTS_SERVICE,
    ROLE_USERS_SERVICE;

    @Override
    public String getAuthority() {
        return name();
    }
}
