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
import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.PriceRateTypeEnum;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.NumberTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.projectrole.ProjectRoleDelegator;
import org.qi4j.chronos.ui.util.ListUtil;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.util.CurrencyUtil;
import org.qi4j.entity.association.SetAssociation;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class PriceRateScheduleAddEditPage extends AddEditBasePage
{
    private MaxLengthTextField nameField;

    private List<SimpleDropDownChoice<ProjectRoleDelegator>> projectRoleChoiceList;
    private List<SimpleDropDownChoice<PriceRateTypeEnum>> priceRateTypeChoiceList;
    private List<NumberTextField> amountFieldList;

    private ListView priceRateListView;

    private SubmitLink newPriceRateLink;

    private List<PriceRate> priceRateList;

    private SimpleDropDownChoice<Currency> currencyChoice;

    private SubmitLink selectPriceRateScheduleLink;

    private WebMarkupContainer selectPriceRateScheduleContainer;

    public PriceRateScheduleAddEditPage( Page goBackPage )
    {
        super( goBackPage );
    }

    protected void hideSelectPriceRateScheduleLink()
    {
//        selectPriceRateScheduleContainer.setVisible( false );
        if( selectPriceRateScheduleContainer.isVisible() )
        {
            selectPriceRateScheduleContainer.setVisibilityAllowed( false );
        }
    }

    private int getTotalAvailablePriceRateSchedule()
    {
        // TODO kamil: migrate
//        return ChronosWebApp.getServices().getPriceRateScheduleService().countAll( getAccount() );
        return getAccount().priceRateSchedules().size();
    }

    public final void initComponent( Form form )
    {
        selectPriceRateScheduleLink = new SubmitLink( "selectPriceRateScheduleLink" )
        {
            public void onSubmit()
            {
                handleSelectPriceRateSchedule();
            }
        };

        selectPriceRateScheduleContainer = new WebMarkupContainer( "selectPriceRateScheduleContainer" );

        selectPriceRateScheduleContainer.add( selectPriceRateScheduleLink );

        if( getTotalAvailablePriceRateSchedule() == 0 )
        {
            selectPriceRateScheduleContainer.setVisible( false );
        }

        currencyChoice = new SimpleDropDownChoice<Currency>( "currencyChoice", CurrencyUtil.getCurrencyList(), true );

        priceRateList = buildPriceRateList( getInitPriceRateIterator() );

        //let create one price rates for default.
        if( priceRateList.size() == 0 )
        {
            addNewPriceRate();
        }

        projectRoleChoiceList = new ArrayList<SimpleDropDownChoice<ProjectRoleDelegator>>();
        priceRateTypeChoiceList = new ArrayList<SimpleDropDownChoice<PriceRateTypeEnum>>();

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
                PriceRate priceRate = priceRateList.get( index );

                SimpleDropDownChoice<ProjectRoleDelegator> projectRoleChoice;
                SimpleDropDownChoice<PriceRateTypeEnum> priceRateTypeChoice;
                NumberTextField amountField;

                if( projectRoleChoiceList.size() <= index )
                {
                    projectRoleChoice = new SimpleDropDownChoice<ProjectRoleDelegator>( "projectRoleChoice", ListUtil.getProjectRoleDelegatorList( getAccount() ), true );
                    priceRateTypeChoice = new SimpleDropDownChoice<PriceRateTypeEnum>( "priceRateTypeChoice", Arrays.asList( PriceRateTypeEnum.values() ), true );

                    amountField = new NumberTextField( "amountField", "Amount" );

                    projectRoleChoiceList.add( projectRoleChoice );
                    priceRateTypeChoiceList.add( priceRateTypeChoice );
                    amountFieldList.add( amountField );

                    //init value
                    projectRoleChoice.setChoice( new ProjectRoleDelegator( priceRate.projectRole().get() ) );
                    priceRateTypeChoice.setChoice( priceRate.priceRateType().get() );
                    amountField.setLongValue( priceRate.amount().get() );
                }
                else
                {
                    projectRoleChoice = projectRoleChoiceList.get( index );
                    priceRateTypeChoice = priceRateTypeChoiceList.get( index );
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
                item.add( amountField );
                item.add( deletePriceRateLink );
            }
        };

        nameField = new MaxLengthTextField( "name", "Name", PriceRateSchedule.NAME_LEN );

        form.add( selectPriceRateScheduleContainer );
        form.add( currencyChoice );
        form.add( newPriceRateLink );
        form.add( priceRateListView );
        form.add( nameField );
    }

    private void handleSelectPriceRateSchedule()
    {
        PriceRateScheduleSelectionPage selectionPage = new PriceRateScheduleSelectionPage( this )
        {
            public void handleSelectedPriceRateSchedule( PriceRateSchedule priceRateSchedule )
            {
                resetPriceRateSchedule( priceRateSchedule );
            }
        };

        setResponsePage( selectionPage );
    }

    private void resetPriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        nameField.setText( "[Customize] " + priceRateSchedule.name().get() );
        currencyChoice.setChoice( priceRateSchedule.currency().get() );

        priceRateList = buildPriceRateList( priceRateSchedule.priceRates().iterator() );

        projectRoleChoiceList.clear();
        priceRateTypeChoiceList.clear();
        amountFieldList.clear();

        updatePriceRateListView();
    }

    private void handleNewPriceRate()
    {
        addNewPriceRate();

        updatePriceRateListView();
    }

    private void addNewPriceRate()
    {
        PriceRate priceRate = getUnitOfWork().newEntityBuilder( PriceRateEntityComposite.class ).newInstance();
        priceRate.priceRateType().set( PriceRateTypeEnum.HOURLY );

        // TODO: migrate to new service
        List<ProjectRole> projectRolelists = new ArrayList<ProjectRole>( getAccount().projectRoles() );

        priceRate.projectRole().set( projectRolelists.get( 0 ) );
        priceRate.amount().set( 0L );

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
        amountFieldList.remove( index );

        updatePriceRateListView();
    }

    private void updatePriceRateListView()
    {
        priceRateListView.setList( Arrays.asList( new Integer[ priceRateList.size() ] ) );
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

    //TODO bp. This can be simplified when we have ValueComposite
    private boolean isHasDuplicatePriceRate()
    {
        for( int i = 0; i < projectRoleChoiceList.size(); i++ )
        {
            ProjectRoleDelegator projectRole = projectRoleChoiceList.get( i ).getChoice();
            PriceRateTypeEnum priceRateType = priceRateTypeChoiceList.get( i ).getChoice();
            long amount = amountFieldList.get( i ).getLongValue();

            for( int j = i + 1; j < projectRoleChoiceList.size(); j++ )
            {
                ProjectRoleDelegator projectRole2 = projectRoleChoiceList.get( j ).getChoice();
                PriceRateTypeEnum priceRateType2 = priceRateTypeChoiceList.get( j ).getChoice();
                long amount2 = amountFieldList.get( j ).getLongValue();

                if( projectRole.getName().equals( projectRole2.getName() )
                    && priceRateType.equals( priceRateType2 )
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

        for( PriceRate priceRate : priceRateList )
        {
            ProjectRole projectRole = getProjectRole( projectRoleChoiceList.get( index ).getChoice() );

            priceRate.projectRole().set( projectRole );
            priceRate.priceRateType().set( priceRateTypeChoiceList.get( index ).getChoice() );
            priceRate.amount().set( amountFieldList.get( index ).getLongValue() );

            index++;
        }
    }

    private ProjectRole getProjectRole( ProjectRoleDelegator projectRoleDelegator )
    {
/*
        TODO kamil: migrate
        ProjectRole projectRole = ChronosWebApp.newInstance( ProjectRoleEntityComposite.class );

        projectRole.name().set( projectRoleDelegator.getName() );

        return projectRole;
*/
        for( ProjectRole projectRole : getAccount().projectRoles() )
        {
            if( projectRoleDelegator.getName().equals( projectRole.name().get() ) )
            {
                return projectRole;
            }
        }

        return null;
    }

    protected void assignFieldValueToPriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        priceRateSchedule.name().set( nameField.getText() );
        priceRateSchedule.currency().set( currencyChoice.getChoice() );

        SetAssociation<PriceRate> priceRates = priceRateSchedule.priceRates();
        priceRates.clear();
        priceRates.addAll( priceRateList );
    }

    protected void assignPriceRateScheduleToFieldValue( PriceRateSchedule priceRateSchedule )
    {
        nameField.setText( priceRateSchedule.name().get() );
        currencyChoice.setChoice( priceRateSchedule.currency().get() );
        //skip initializing priceRate as it is done in getInitPriceRateInit
    }

    private List<PriceRate> buildPriceRateList( Iterator<PriceRate> priceRateIterator )
    {
        List<PriceRate> priceRateList = new ArrayList<PriceRate>();

        while( priceRateIterator.hasNext() )
        {
            priceRateList.add( priceRateIterator.next() );
        }

        return priceRateList;
    }


    public abstract Iterator<PriceRate> getInitPriceRateIterator();

    public abstract void onSubmitting();
}
