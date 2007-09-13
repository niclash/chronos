///*
// * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
//package org.qi4j.chronos.model.mixins;
//
//import java.util.Currency;
//import org.qi4j.api.annotation.Uses;
//import org.qi4j.library.general.model.Descriptor;
//import org.qi4j.library.general.model.Money;
//
///**
// * Provide the implementation of displaying money. For example: 100 MYR, 1000 USD, 250 AUD.
// */
//public final class MoneyDescriptorMixin implements Descriptor
//{
//    @Uses private Money money;
//
//    public String getDisplayValue()
//    {
//        Currency currency = money.getCurrency();
//        String currencyCode = "";
//        if( currency != null )
//        {
//            currencyCode = Character.SPACE_SEPARATOR + currency.getCurrencyCode();
//        }
//
//        long amount = money.getAmount();
//        return amount + currencyCode;
//    }
//
//    public void setDisplayValue( String aDisplayValue )
//    {
//        // Do nothing
//    }
//}
