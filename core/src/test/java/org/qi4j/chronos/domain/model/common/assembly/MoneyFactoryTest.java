/*  Copyright 2008 Edward Yakop.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.domain.model.common.assembly;

import java.util.Currency;
import static org.junit.Assert.*;
import org.junit.Test;
import org.qi4j.api.service.ServiceReference;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.chronos.domain.model.common.money.Money;
import org.qi4j.chronos.domain.model.common.money.MoneyFactory;

public class MoneyFactoryTest extends AbstractCommonTest
{
    @Test
    public void createMoneyTest()
    {
        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();

        ServiceReference<MoneyFactory> serviceRef = moduleInstance.serviceFinder().findService( MoneyFactory.class );
        MoneyFactory moneyFactory = serviceRef.get();
        try
        {
            Currency currency = Currency.getInstance( "USD" );
            Money money = moneyFactory.create( currency, 100 );
            assertEquals( 100, money.amount() );
            assertEquals( currency, money.currency() );

            uow.remove( money );
        }
        finally
        {
            uow.discard();
        }
    }
}
