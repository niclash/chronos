package org.qi4j.chronos.model;

import java.util.List;

public interface HasLegalConditions
{
    void addLegalCondition(LegalCondition legalCondition);

    void removeLegalCondition(LegalCondition legalCondition);

    List<LegalCondition> getLegalConditions();
}
