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
package org.qi4j.chronos.ui.pricerate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.PriceRateType;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.NumberTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.projectrole.ProjectRoleDelegator;
import org.qi4j.chronos.ui.util.ListUtil;
import org.qi4j.chronos.util.CurrencyUtil;

public abstract class PriceRateScheduleAddEditPage extends AddEditBasePage
{
    private MaxLengthTextField nameField;

    private List<SimpleDropDownChoice<ProjectRoleDelegator>> projectRoleChoiceList;
    private List<SimpleDropDownChoice<PriceRateType>> priceRateTypeChoiceList;
    private List<SimpleDropDownChoice<Currency>> currencyChoiceList;
    private List<NumberTextField> amountFieldList;

    private ListView priceRateListView;

    private SubmitLink newPriceRateLink;

    //TODO remove static
    private static List<PriceRateComposite> priceRateList;

    public PriceRateScheduleAddEditPage( BasePage goBackPage )
    {
        super( goBackPage );
    }

    public final void initComponent( Form form )
    {
        priceRateList = getInitPriceRateList();

        //let create one price rates for default.
        if( priceRateList.size() == 0 )
        {
            addNewPriceRate();
        }

        projectRoleChoiceList = new ArrayList<SimpleDropDownChoice<ProjectRoleDelegator>>();
        priceRateTypeChoiceList = new ArrayList<SimpleDropDownChoice<PriceRateType>>();
        currencyChoiceList = new ArrayList<SimpleDropDownChoice<Currency>>();

        amountFieldList = new ArrayList<NumberTextField>();

        newPriceRateLink = new SubmitLink( "newPriceRateLink" )
        {
            public void onSubmit()
            {
                handleNewPriceRate();
            }
        };

        priceRateListView = new ListView( "priceRateListView", Arrays.asList( new Integer[priceRateList.size()] ) )
        {
            protected void populateItem( ListItem item )
            {
                final int index = item.getIndex();
                PriceRateComposite priceRate = priceRateList.get( index );

                SimpleDropDownChoice<ProjectRoleDelegator> projectRoleChoice;
                SimpleDropDownChoice<PriceRateType> priceRateTypeChoice;
                SimpleDropDownChoice<Currency> currencyChoice;
                NumberTextField amountField;

                if( projectRoleChoiceList.size() <= index )
                {
                    projectRoleChoice = new SimpleDropDownChoice<ProjectRoleDelegator>( "projectRoleChoice", ListUtil.getProjectRoleDelegatorList( getAccount() ), true );
                    priceRateTypeChoice = new SimpleDropDownChoice<PriceRateType>( "priceRateTypeChoice", Arrays.asList( PriceRateType.values() ), true );
                    currencyChoice = new SimpleDropDownChoice<Currency>( "currencyChoice", CurrencyUtil.getCurrencyList(), true );

                    amountField = new NumberTextField( "amountField", "Amount" );

                    projectRoleChoiceList.add( projectRoleChoice );
                    priceRateTypeChoiceList.add( priceRateTypeChoice );
                    currencyChoiceList.add( currencyChoice );
                    amountFieldList.add( amountField );

                    //init value
                    projectRoleChoice.setChoice( new ProjectRoleDelegator( priceRate.getProjectRole() ) );
                    priceRateTypeChoice.setChoice( priceRate.getPriceRateType() );
                    currencyChoice.setChoice( priceRate.getCurrency() );
                    amountField.setLongValue( priceRate.getAmount() );
                }
                else
                {
                    projectRoleChoice = projectRoleChoiceList.get( index );
                    priceRateTypeChoice = priceRateTypeChoiceList.get( index );
                    currencyChoice = currencyChoiceList.get( index );
                    amountField = amountFieldList.get( index );
                }

                SubmitLink deletePriceRateLink = new SubmitLink( "deletePriceRateLink" )
                {
                    public void onSubmit()
                    {
                        removePriceRate( index );
                    }
                };

                item.add( projectRoleChoice );
                item.add( priceRateTypeChoice );
                item.add( currencyChoice );
                item.add( amountField );
                item.add( deletePriceRateLink );
            }
        };

        nameField = new MaxLengthTextField( "name", "Name", PriceRateSchedule.NAME_LEN );

        form.add( newPriceRateLink );
        form.add( priceRateListView );
        form.add( nameField );
    }

    private void handleNewPriceRate()
    {
        addNewPriceRate();

        updatePriceRateListView();
    }

    private void addNewPriceRate()
    {
        PriceRateComposite priceRate = ChronosWebApp.newInstance( PriceRateComposite.class );

        priceRate.setPriceRateType( PriceRateType.hourly );

        List<ProjectRoleEntityComposite> projectRolelists = ChronosWebApp.getServices().getProjectRoleService().findAll( getAccount() );

        priceRate.setProjectRole( projectRolelists.get( 0 ) );
        priceRate.setCurrency( CurrencyUtil.getDefaultCurrency() );
        priceRate.setAmount( 0L );

        priceRateList.add( priceRate );
    }

    private void removePriceRate( int index )
    {
        if( priceRateList.size() == 0 )
        {
            return;
        }

        priceRateList.remove( index );

        projectRoleChoiceList.remove( index );
        priceRateTypeChoiceList.remove( index );
        currencyChoiceList.remove( index );
        amountFieldList.remove( index );

        updatePriceRateListView();
    }

    private void updatePriceRateListView()
    {
        priceRateListView.setList( Arrays.asList( new Integer[priceRateList.size()] ) );
    }

    public final void handleSubmit()
    {
        boolean isRejected = false;

        if( nameField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( priceRateList.size() == 0 )
        {
            error( "Please add at least one Price Rate." );

            isRejected = true;
        }

        if( isInvalidPriceRate() || isHasDuplicatePriceRate() )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }

        fillupPriceRate();

        onSubmitting();
    }

    private boolean isInvalidPriceRate()
    {
        for( NumberTextField amountField : amountFieldList )
        {
            if( amountField.checkIsEmptyOrNotLong() )
            {
                return true;
            }
        }

        return false;
    }

    private boolean isHasDuplicatePriceRate()
    {
        for( int i = 0; i < projectRoleChoiceList.size(); i++ )
        {
            ProjectRoleDelegator projectRole = projectRoleChoiceList.get( i ).getChoice();
            PriceRateType priceRateType = priceRateTypeChoiceList.get( i ).getChoice();
            Currency currency = currencyChoiceList.get( i ).getChoice();
            long amount = amountFieldList.get( i ).getLongValue();

            for( int j = i + 1; j < projectRoleChoiceList.size(); j++ )
            {
                ProjectRoleDelegator projectRole2 = projectRoleChoiceList.get( j ).getChoice();
                PriceRateType priceRateType2 = priceRateTypeChoiceList.get( j ).getChoice();
                Currency currency2 = currencyChoiceList.get( j ).getChoice();
                long amount2 = amountFieldList.get( j ).getLongValue();

                if( projectRole.getId().equals( projectRole2.getId() )
                    && priceRateType.equals( priceRateType2 )
                    && currency.equals( currency2 )
                    && amount == amount2 )
                {
                    error( "Identical price rate found! Please change it or remove it." );

                    return true;
                }
            }
        }

        return false;
    }

    private void fillupPriceRate()
    {
        int index = 0;

        for( PriceRateComposite priceRate : priceRateList )
        {
            ProjectRoleEntityComposite projectRole = getProjectRole( projectRoleChoiceList.get( index ).getChoice() );

            priceRate.setProjectRole( projectRole );
            priceRate.setPriceRateType( priceRateTypeChoiceList.get( index ).getChoice() );
            priceRate.setCurrency( currencyChoiceList.get( index ).getChoice() );
            priceRate.setAmount( amountFieldList.get( index ).getLongValue() );

            index++;
        }
    }

    private ProjectRoleEntityComposite getProjectRole( ProjectRoleDelegator projectRoleDelegator )
    {
        return ChronosWebApp.getServices().getProjectRoleService().get( projectRoleDelegator.getId() );
    }

    protected void assignFieldValueToPriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        priceRateSchedule.setName( nameField.getText() );

        priceRateSchedule.removeAllPriceRate();

        for( PriceRateComposite priceRate : priceRateList )
        {
            priceRateSchedule.addPriceRate( priceRate );
        }
    }

    protected void assignPriceRateScheduleToFieldValue( PriceRateSchedule priceRateSchedule )
    {
        nameField.setText( priceRateSchedule.getName() );

        //skip initializing priceRate as it is done in getInitPriceRateInit
    }

    private List<PriceRateComposite> getInitPriceRateList()
    {
        List<PriceRateComposite> priceRateList = new ArrayList<PriceRateComposite>();

        Iterator<PriceRateComposite> priceRateIterator = getInitPriceRateIterator();

        while( priceRateIterator.hasNext() )
        {
            priceRateList.add( priceRateIterator.next() );
        }

        return priceRateList;
    }

    public abstract Iterator<PriceRateComposite> getInitPriceRateIterator();

    public abstract void onSubmitting();
}
