/*
 * Copyright (c) 2008, kamil. All Rights Reserved.
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
package org.qi4j.chronos.util.test;

import java.util.Currency;
import static org.junit.Assert.*;
import org.junit.Test;
import org.qi4j.chronos.util.CurrencyUtil;

public class CurrencyUtilTest
{
    @Test
    public void currencyTest()
    {
        System.out.println( "CurrencyUtilTest.currencyTest" );
        
        Currency USD = Currency.getInstance( "USD" );
        assertEquals( "Default currency is not equals to USD!!!", USD, CurrencyUtil.getDefaultCurrency() );

        for( Currency currency : CurrencyUtil.getCurrencyList() )
        {
            System.out.println( "Currency: " + currency.toString() );
        }

        assertEquals( "Currency list size is not equal to 4", 4, CurrencyUtil.getCurrencyList().size() );

/*
        for( Currency currency : CurrencyUtil.getCurrencyList() )
        {

        }
*/
    }
}
