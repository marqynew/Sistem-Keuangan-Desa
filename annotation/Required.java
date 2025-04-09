package annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Required {
    // Menandakan field wajib diinisialisasi (mirip dependency injection requirement)
}
