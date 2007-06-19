package org.qi4j.chronos.model;

import org.qi4j.api.annotation.ModifiedBy;

@ModifiedBy(GenderTypeValidator.class)
public interface Gender
{
    String getGender();

    void setGender(String gender);
}
