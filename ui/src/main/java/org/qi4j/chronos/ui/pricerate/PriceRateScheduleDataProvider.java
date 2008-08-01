package org.qi4j.chronos.ui.pricerate;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class PriceRateScheduleDataProvider extends AbstractSortableDataProvider<PriceRateSchedule>
{
    private static final long serialVersionUID = 1L;

    private IModel<HasPriceRateSchedules> hasPriceRateSchedules;

    public PriceRateScheduleDataProvider( IModel<HasPriceRateSchedules> hasPriceRateSchedules )
    {
        this.hasPriceRateSchedules = hasPriceRateSchedules;
    }

    public IModel<PriceRateSchedule> load( String id )
    {
        PriceRateSchedule priceRateSchedule = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, PriceRateSchedule.class );

        return new ChronosDetachableModel<PriceRateSchedule>( priceRateSchedule );
    }

    public List<PriceRateSchedule> dataList( int first, int count )
    {
        //TODO
        List<PriceRateSchedule> priceRateSchedules = new ArrayList<PriceRateSchedule>( hasPriceRateSchedules.getObject().priceRateSchedules() );

        return priceRateSchedules.subList( first, first + count );
    }

    public int size()
    {
        return hasPriceRateSchedules.getObject().priceRateSchedules().size();
    }
}
