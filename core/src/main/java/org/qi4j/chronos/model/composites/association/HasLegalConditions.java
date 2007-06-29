package org.qi4j.chronos.model.composites.association;

import java.util.List;
import java.util.Iterator;
import java.io.Serializable;
import org.qi4j.chronos.model.composites.LegalConditionComposite;

public interface HasLegalConditions extends Serializable
{
    void addLegalCondition(LegalConditionComposite legalCondition);

    void removeLegalCondition( LegalConditionComposite legalCondition);

    Iterator<LegalConditionComposite> legalConditionIterator();
}
