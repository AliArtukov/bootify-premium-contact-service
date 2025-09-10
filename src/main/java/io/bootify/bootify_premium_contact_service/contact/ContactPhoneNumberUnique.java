package io.bootify.bootify_premium_contact_service.contact;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the phoneNumber value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = ContactPhoneNumberUnique.ContactPhoneNumberUniqueValidator.class
)
public @interface ContactPhoneNumberUnique {

    String message() default "{Exists.contact.phoneNumber}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ContactPhoneNumberUniqueValidator implements ConstraintValidator<ContactPhoneNumberUnique, String> {

        private final ContactService contactService;
        private final HttpServletRequest request;

        public ContactPhoneNumberUniqueValidator(final ContactService contactService,
                final HttpServletRequest request) {
            this.contactService = contactService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(contactService.get(Long.parseLong(currentId)).getPhoneNumber())) {
                // value hasn't changed
                return true;
            }
            return !contactService.phoneNumberExists(value);
        }

    }

}
