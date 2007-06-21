package org.qi4j.chronos.model.modifiers;

public class ValidatorModifierException extends RuntimeException
{
    public ValidatorModifierException(String message)
    {
        super(message);        
    }
    
    public ValidatorModifierException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
