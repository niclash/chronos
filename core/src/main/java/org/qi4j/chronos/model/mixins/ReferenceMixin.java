package org.qi4j.chronos.model.mixins;

import org.qi4j.chronos.model.Reference;

public class ReferenceMixin implements Reference
{
    private String reference;

    public String getReference()
    {
        return reference;
    }

    public void setReference( String reference )
    {
        this.reference = reference;
    }
}
