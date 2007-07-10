package org.qi4j.chronos.model.mixins;

import org.qi4j.library.general.model.Name;

public class NameMixin implements Name
{
    private String name;

    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
