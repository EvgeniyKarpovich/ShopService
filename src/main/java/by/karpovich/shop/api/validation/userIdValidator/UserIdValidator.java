package by.karpovich.shop.api.validation.userIdValidator;

import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.jpa.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserIdValidator implements ConstraintValidator<ValidUserId, Long> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        Optional<UserEntity> entity = userRepository.findById(id);
        return entity.isPresent();
    }
}
