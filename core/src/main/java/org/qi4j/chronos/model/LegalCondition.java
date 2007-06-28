package org.qi4j.chronos.model;

import org.qi4j.chronos.model.modifiers.NotNullable;
import org.qi4j.api.annotation.ModifiedBy;

public interface LegalCondition
{
    void setLegalCondition(String legalCondition);

    String getLegalCondition();
}
