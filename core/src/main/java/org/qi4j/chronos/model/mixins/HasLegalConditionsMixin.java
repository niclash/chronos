package org.qi4j.chronos.model.mixins;

import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.associations.HasLegalConditions;

public class HasLegalConditionsMixin implements HasLegalConditions
{
    private List<LegalCondition> list;

    public void addLegalCondition( LegalCondition legalCondition )
    {
        list.add( legalCondition );
    }

    public void removeLegalCondition( LegalCondition legalCondition )
    {
        list.remove( legalCondition );
    }

    public Iterator<LegalCondition> legalConditionIterator()
    {
        return list.iterator();
    }
}
