package annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Provide {
    // Menandakan bahwa method menyediakan instance (mirip provider factory)
}
