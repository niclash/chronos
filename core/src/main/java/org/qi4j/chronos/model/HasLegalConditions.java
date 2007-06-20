package org.qi4j.chronos.model;

import java.util.List;
import org.qi4j.chronos.model.composites.LegalConditionComposite;

public interface HasLegalConditions
{
    void addLegalCondition(LegalConditionComposite legalCondition);

    void removeLegalCondition( LegalConditionComposite legalCondition);

    List<LegalConditionComposite> getLegalConditions();
}
