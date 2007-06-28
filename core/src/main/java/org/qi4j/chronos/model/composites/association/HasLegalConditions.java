package org.qi4j.chronos.model.composites.association;

import java.util.List;
import java.util.Iterator;
import org.qi4j.chronos.model.composites.LegalConditionComposite;

public interface HasLegalConditions
{
    void addLegalCondition(LegalConditionComposite legalCondition);

    void removeLegalCondition( LegalConditionComposite legalCondition);

    Iterator<LegalConditionComposite> legalConditionIterator();
}
