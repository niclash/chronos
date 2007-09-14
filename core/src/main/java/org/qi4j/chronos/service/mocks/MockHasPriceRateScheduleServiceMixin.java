///*
// * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.qi4j.chronos.service.mocks;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import org.qi4j.api.annotation.Uses;
//import org.qi4j.chronos.model.PriceRateSchedule;
//import org.qi4j.chronos.model.composites.AccountEntityComposite;
//import org.qi4j.chronos.service.AccountService;
//import org.qi4j.chronos.service.associations.HasPriceRateScheduleService;
//
//public class MockHasPriceRateScheduleServiceMixin implements HasPriceRateScheduleService
//{
//    @ThisAs private AccountService accountService;
//
//    public PriceRateSchedule getPriceRateSchedule( String accountId, String priceRateScheduleName )
//    {
//        //TODO bp. temp solution. Actual implementation would be using query.
//        AccountEntityComposite account = accountService.get( accountId );
//
//        Iterator<PriceRateSchedule> priceRateScheduleIterator = account.priceRateScheduleIterator();
//
//        while( priceRateScheduleIterator.hasNext() )
//        {
//            PriceRateSchedule rateSchedule = priceRateScheduleIterator.next();
//
//            if( rateSchedule.getName().equals( priceRateScheduleName ) )
//            {
//                return rateSchedule;
//            }
//        }
//
//        return null;
//    }
//
//    public List<PriceRateSchedule> findPriceRateSchedule( String accountId, int first, int count )
//    {
//        //TODO bp. temp solution. Actual implementation would be using query.
//        AccountEntityComposite account = accountService.get( accountId );
//
//        Iterator<PriceRateSchedule> priceRateScheduleIterator = account.priceRateScheduleIterator();
//
//        List<PriceRateSchedule> rateSchedules = new ArrayList<PriceRateSchedule>();
//
//        while( priceRateScheduleIterator.hasNext() )
//        {
//            rateSchedules.add( priceRateScheduleIterator.next() );
//        }
//
//        return rateSchedules.subList( first, first + count );
//    }
//
//    public int countTotalPriceRateSchedule( String accountId )
//    {
//        //TODO bp. temp solution. Actual implementation would be using query.
//        AccountEntityComposite account = accountService.get( accountId );
//
//        return account.countTotalPriceRateSchedule();
//    }
//}
