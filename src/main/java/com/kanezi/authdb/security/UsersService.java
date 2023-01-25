package com.kanezi.authdb.security;

import com.kanezi.authdb.security.db.UserEntity;
import com.kanezi.authdb.security.db.UserEntityRepository;
import com.kanezi.authdb.security.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record UsersService(
        UserEntityRepository userEntityRepository,
        PasswordEncoder encoder
) implements UserDetailsService {

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findUserEntityByEmail(username)
                                   .map(AppUser::new)
                                   .orElseThrow(() -> new UsernameNotFoundException("username"));
    }

    public AppUser registerUser(String email, String username, String password) {
        UserEntity newUser = new UserEntity();

        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(encoder.encode(password));

        newUser = userEntityRepository.save(newUser);

        return new AppUser(newUser);
    }
}
