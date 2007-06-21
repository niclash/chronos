package org.qi4j.chronos.model.modifiers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention( RetentionPolicy.RUNTIME )
@Target({ ElementType.METHOD })
public @interface StringLength
{
    int maxLength();
}
