package org.qi4j.chronos.model;

import org.qi4j.api.annotation.Modifies;

public final class GenderTypeValidator implements Gender
{
    @Modifies Gender next;

    public String getGender()
    {
        return next.getGender();
    }

    public void setGender( String gender )
    {
        String lowercaseGender = gender.toLowerCase();

        try
        {
            GenderType genderType = GenderType.valueOf( lowercaseGender );
            next.setGender( genderType.toString() );
        }
        catch( IllegalArgumentException e )
        {
            throw new IllegalArgumentException( "Incorrect gender is specified : [" + gender + "]");

        }
    }
}
