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
package org.qi4j.chronos.ui.util;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.PriceRateType;
import org.qi4j.chronos.model.ProjectStatus;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.model.associations.HasPriceRates;
import org.qi4j.chronos.model.associations.HasStaffs;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectRoleComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.pricerate.PriceRateDelegator;
import org.qi4j.chronos.ui.projectrole.ProjectRoleDelegator;
import org.qi4j.chronos.ui.staff.StaffDelegator;
import org.qi4j.chronos.util.CurrencyUtil;
import org.qi4j.library.general.model.GenderType;

public final class ListUtil
{
    public static List<String> getPriceRateScheduleNameList( HasPriceRateSchedules hasPriceRateSchedules )
    {
        Iterator<PriceRateScheduleComposite> iter = hasPriceRateSchedules.priceRateScheduleIterator();
        List<String> nameList = new ArrayList<String>();

        while( iter.hasNext() )
        {
            nameList.add( iter.next().getName() );
        }

        return nameList;
    }


    public static List<StaffDelegator> getStaffDelegator( HasStaffs hasStaffs )
    {
        Iterator<StaffEntityComposite> staffIterator = hasStaffs.staffIterator();
        List<StaffDelegator> staffDelegatorList = new ArrayList<StaffDelegator>();

        while( staffIterator.hasNext() )
        {
            staffDelegatorList.add( new StaffDelegator( staffIterator.next() ) );
        }

        return staffDelegatorList;
    }


    public static List<PriceRateDelegator> getPriceRateDelegator( HasPriceRates hasPriceRates )
    {
        Iterator<PriceRateComposite> priceRateIterator = hasPriceRates.priceRateIterator();

        List<PriceRateDelegator> priceRateList = new ArrayList<PriceRateDelegator>();

        while( priceRateIterator.hasNext() )
        {
            priceRateList.add( new PriceRateDelegator( priceRateIterator.next() ) );
        }

        return priceRateList;
    }

    public static List<ProjectRoleDelegator> getProjectRoleDelegatorList( AccountEntityComposite account )
    {
        List<ProjectRoleComposite> projectRolelists = ChronosWebApp.getServices().getProjectRoleService().findAll( account );

        List<ProjectRoleDelegator> resultList = new ArrayList<ProjectRoleDelegator>();

        for( ProjectRoleComposite projectRole : projectRolelists )
        {
            resultList.add( new ProjectRoleDelegator( projectRole ) );
        }

        return resultList;
    }

    public static List<String> getCurrencyList()
    {
        List<Currency> list = CurrencyUtil.getCurrencyList();
        List<String> resultList = new ArrayList<String>();

        for( Currency currency : list )
        {
            resultList.add( currency.getCurrencyCode() );
        }

        return resultList;
    }

    public static List<String> getPriceRateTypeList()
    {
        PriceRateType[] priceRateTypes = PriceRateType.values();

        List<String> list = new ArrayList<String>();

        for( PriceRateType priceRateType : priceRateTypes )
        {
            list.add( priceRateType.toString() );
        }

        return list;
    }

    public static List<String> getGenderTypeList()
    {
        GenderType[] genderTypes = GenderType.values();
        List<String> result = new ArrayList<String>();

        for( GenderType genderType : genderTypes )
        {
            result.add( genderType.toString() );
        }

        return result;
    }

    public static List<String> getProjectStatusList()
    {
        ProjectStatus[] projectStatuses = ProjectStatus.values();

        List<String> result = new ArrayList<String>();

        for( ProjectStatus projectStatus : projectStatuses )
        {
            result.add( projectStatus.toString() );
        }

        return result;
    }
}
