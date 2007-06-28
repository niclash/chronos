package org.qi4j.chronos.test.model1;

import org.qi4j.chronos.model.modifiers.NotNullable;
import org.qi4j.chronos.model.modifiers.StringLength;

public class AddressImpl implements Address
{
    private String address1;
    private String address2;

    @StringLength(minLength = 2 )
    @NotNullable
    public void setAddress1( String address1 )
    {
        this.address1 = address1;
    }

    public String getAddress1()
    {
        return address1;
    }

    @StringLength(maxLength = 20 )
    public void setAddress2( String address2 )
    {
        this.address2 = address2;
    }

    public String getAddress2()
    {
        return address2;
    }
}
