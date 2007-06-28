package org.qi4j.chronos.model.mixins;

import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.Role;
import org.qi4j.chronos.model.modifiers.NotNullValidationModifier;
import org.qi4j.chronos.model.modifiers.NotNullable;

@ModifiedBy( { NotNullValidationModifier.class } )
public class RoleMixin implements Role
{
    private String role;

    public String getRole()
    {
        return role;
    }

    @NotNullable
    public void setRole( String role )
    {
        this.role = role;
    }
}
