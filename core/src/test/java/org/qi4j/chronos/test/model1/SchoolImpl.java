package org.qi4j.chronos.test.model1;

import org.qi4j.chronos.model.modifiers.NotNullable;

public class SchoolImpl implements School
{
    private String schoolName;
    private String type;

    @NotNullable
    public void setSchoolName( String schoolName )
    {
        this.schoolName = schoolName;
    }

    public String getSchoolName()
    {
        return schoolName;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
}
