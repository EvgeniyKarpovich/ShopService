package by.karpovich.shop.api.validation.emailValidator;

import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.jpa.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) {
            return false;
        }
        Optional<UserEntity> entity = userRepository.findByEmail(email);
        return !entity.isPresent();
    }
}
