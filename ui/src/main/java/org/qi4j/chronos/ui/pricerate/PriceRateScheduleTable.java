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
//package org.qi4j.chronos.ui.pricerate;
//
//import java.util.Arrays;
//import java.util.List;
//import org.apache.wicket.markup.repeater.Item;
//import org.qi4j.chronos.model.PriceRateSchedule;
//import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
//import org.qi4j.chronos.ui.common.SimpleLink;
//import org.qi4j.chronos.ui.common.action.ActionTable;
//
//public abstract class PriceRateScheduleTable extends ActionTable<PriceRateSchedule>
//{
//    private PriceRateScheduleDataProvider dataProvider;
//
//    public PriceRateScheduleTable( String id )
//    {
//        super( id );
//    }
//
//    public AbstractSortableDataProvider<PriceRateSchedule> getDetachableDataProvider()
//    {
//        if( dataProvider == null )
//        {
//            dataProvider = new PriceRateScheduleDataProvider( getAccountId() );
//        }
//
//        return null;
//    }
//
//    public void populateItems( Item item, PriceRateSchedule obj )
//    {
//        item.add( new SimpleLink( "accountName", obj.getName() )
//        {
//            public void linkClicked()
//            {
//                //TODO bp. fixme
//            }
//        } );
//
//        item.add( new SimpleLink( "editLink", "Edit" )
//        {
//            public void linkClicked()
//            {
//                //TODO bp. fixme
//            }
//        } );
//    }
//
//    public List<String> getTableHeaderList()
//    {
//        return Arrays.asList( "Name", "" );
//    }
//
//    public abstract String getAccountId();
//}
