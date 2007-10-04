/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.service.mocks;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.annotation.scope.Qi4j;
import org.qi4j.chronos.model.PriceRateType;
import org.qi4j.chronos.model.associations.HasPriceRates;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.PriceRateService;

public class MockPriceRateServiceMixin implements PriceRateService
{
    @Qi4j private CompositeBuilderFactory factory;

    public List<PriceRateComposite> findAll( HasPriceRates hasPriceRates, FindFilter findFilter )
    {
        return findAll( hasPriceRates ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<PriceRateComposite> findAll( HasPriceRates hasPriceRates )
    {
        final List<PriceRateComposite> list = new ArrayList<PriceRateComposite>();

        loopPriceRate( hasPriceRates, new LoopCallBack<PriceRateComposite>()
        {
            public boolean callBack( PriceRateComposite priceRate )
            {
                list.add( CloneUtil.clonePriceRate( factory, priceRate ) );

                return true;
            }
        } );

        return list;
    }

    public int countAll( HasPriceRates hasPriceRates )
    {
        return findAll( hasPriceRates ).size();
    }

    private void loopPriceRate( HasPriceRates hasPriceRates, LoopCallBack<PriceRateComposite> loopCallBack )
    {
        Iterator<PriceRateComposite> iter = hasPriceRates.priceRateIterator();

        while( iter.hasNext() )
        {
            boolean next = loopCallBack.callBack( iter.next() );

            if( !next )
            {
                break;
            }
        }
    }

    public PriceRateComposite get( HasPriceRates hasPriceRates, final String projectRoleName, final PriceRateType priceRateType,
                                   final Currency currency, final long amount )
    {
        final PriceRateComposite[] returnValue = new PriceRateComposite[1];

        loopPriceRate( hasPriceRates, new LoopCallBack<PriceRateComposite>()
        {
            public boolean callBack( PriceRateComposite priceRate )
            {
                if( priceRate.getProjectRole().getProjectRole().equals( projectRoleName )
                    && priceRate.getPriceRateType().equals( priceRateType )
                    && priceRate.getAmount().equals( amount ) )
                {
                    returnValue[ 0 ] = CloneUtil.clonePriceRate( factory, priceRate );

                    return false;
                }

                return true;
            }
        } );

        return returnValue[ 0 ];
    }

    public void update( PriceRateComposite priceRate )
    {
        //nothing to do.
    }
}
