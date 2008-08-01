package org.qi4j.chronos.ui.legalcondition;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.associations.HasLegalConditions;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class LegalConditionDataProvider extends AbstractSortableDataProvider<LegalCondition>
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasLegalConditions> hasLegalConditions;

    public LegalConditionDataProvider( IModel<? extends HasLegalConditions> hasLegalConditions )
    {
        this.hasLegalConditions = hasLegalConditions;
    }

    public IModel<LegalCondition> load( String id )
    {
        LegalCondition legalCondition = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, LegalCondition.class );

        return new ChronosDetachableModel<LegalCondition>( legalCondition );
    }

    public List<LegalCondition> dataList( int first, int count )
    {
        //TODO
        List<LegalCondition> legalConditions = new ArrayList<LegalCondition>( hasLegalConditions.getObject().legalConditions() );

        return legalConditions.subList( first, first + count );
    }

    public int size()
    {
        return hasLegalConditions.getObject().legalConditions().size();
    }
}
