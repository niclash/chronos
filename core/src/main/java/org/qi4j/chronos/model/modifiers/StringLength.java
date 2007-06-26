package org.qi4j.chronos.model.modifiers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target({ ElementType.METHOD })
public @interface StringLength
{
    int maxLength() default 0;

    int minLength() default 0;
}
