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
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.PriceRateTypeEnum;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.PriceRateEntityComposite;
import org.qi4j.chronos.ui.common.CurrencyChoiceRenderer;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.NameChoiceRenderer;
import org.qi4j.chronos.ui.common.NumberTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.common.model.CustomCompositeModel;
import org.qi4j.chronos.ui.common.model.GenericCustomCompositeModel;
import org.qi4j.chronos.ui.common.model.NameModel;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.util.CurrencyUtil;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class PriceRateScheduleAddEditPage extends AddEditBasePage
{
    private MaxLengthTextField nameField;
    private List<SimpleDropDownChoice<ProjectRole>> projectRoleChoiceList;
    private List<SimpleDropDownChoice<PriceRateTypeEnum>> priceRateTypeChoiceList;
    private List<NumberTextField> amountFieldList;
    protected List<PriceRate> priceRateList;
    private ListView priceRateListView;
    private SubmitLink newPriceRateLink;
    private SubmitLink selectPriceRateScheduleLink;
    private SimpleDropDownChoice currencyChoice;
    private WebMarkupContainer selectPriceRateScheduleContainer;
    final IChoiceRenderer currencyChoiceRenderer = new CurrencyChoiceRenderer();
    final IChoiceRenderer nameChoiceRenderer = new NameChoiceRenderer();
    private static final String DUPLICATE_ENTRY = "duplicateEntry";

    public PriceRateScheduleAddEditPage( Page goBackPage, IModel<PriceRateSchedule> priceRateScheduleModel )
    {
        super( goBackPage, priceRateScheduleModel );
    }

    protected void hideSelectPriceRateScheduleLink()
    {
        if( selectPriceRateScheduleContainer.isVisible() )
        {
            selectPriceRateScheduleContainer.setVisibilityAllowed( false );
        }
    }

    private int getTotalAvailablePriceRateSchedule()
    {
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


        currencyChoice =
            new SimpleDropDownChoice( "currencyChoice", CurrencyUtil.getCurrencyList(), true );

        priceRateList = buildPriceRateList( getInitPriceRateIterator() );
        projectRoleChoiceList = new ArrayList<SimpleDropDownChoice<ProjectRole>>();
        priceRateTypeChoiceList = new ArrayList<SimpleDropDownChoice<PriceRateTypeEnum>>();
        amountFieldList = new ArrayList<NumberTextField>();

        newPriceRateLink = new SubmitLink( "newPriceRateLink" )
        {
            public void onSubmit()
            {
                handleNewPriceRate( );
            }
        };

        priceRateListView = new ListView( "priceRateListView", Arrays.asList( new Integer[priceRateList.size()] ) )
        {
            protected void populateItem( ListItem item )
            {
                final int index = item.getIndex();
                final PriceRate priceRate = priceRateList.get( index );

                final SimpleDropDownChoice<ProjectRole> projectRoleChoice =
                    new SimpleDropDownChoice( "projectRoleChoice",
                                              new ArrayList( getAccount().projectRoles() ), nameChoiceRenderer );
                final SimpleDropDownChoice<PriceRateTypeEnum> priceRateTypeChoice =
                    new SimpleDropDownChoice( "priceRateTypeChoice",
                                              Arrays.asList( PriceRateTypeEnum.values() ), true );
                final NumberTextField amountField = new NumberTextField( "amountField", "Amount" );

                projectRoleChoiceList.add( projectRoleChoice );
                priceRateTypeChoiceList.add( priceRateTypeChoice );
                amountFieldList.add( amountField );

                // bind model
                IModel iModel = new CompoundPropertyModel( priceRate );
                projectRoleChoice.setModel( new CustomCompositeModel( iModel, "projectRole" ) );
                priceRateTypeChoice.setModel( new CustomCompositeModel( iModel, "priceRateType" ) );
                amountField.setModel( new GenericCustomCompositeModel<Long>( iModel, "amount" ) );

                final SubmitLink deletePriceRateLink = new SubmitLink( "deletePriceRateLink" )
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
        nameField.setModelObject( "[Customize] " + priceRateSchedule.name().get() );
        currencyChoice.setModelObject( priceRateSchedule.currency().get() );

        priceRateList = buildPriceRateList( priceRateSchedule.priceRates().iterator() );

        projectRoleChoiceList.clear();
        priceRateTypeChoiceList.clear();
        amountFieldList.clear();

        updatePriceRateListView();
    }

    private void handleNewPriceRate(  )
    {
        addNewPriceRate( );

        updatePriceRateListView();
    }

    protected void addNewPriceRate( )
    {
        PriceRate priceRate = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().newEntityBuilder( PriceRateEntityComposite.class ).newInstance();
        priceRate.priceRateType().set( PriceRateTypeEnum.HOURLY );

        priceRate.projectRole().set( getAccount().projectRoles().iterator().next() );
        priceRate.amount().set( 0L );

        priceRateList.add( priceRate );
    }

    private void removePriceRate( int index )
    {
        if( priceRateList.size() == 1 )
        {
            error( "Must have at least one price rate!" );
            return;
        }

        priceRateList.remove( index );

        projectRoleChoiceList.remove( index );
        priceRateTypeChoiceList.remove( index );
        amountFieldList.remove( index );

        updatePriceRateListView();
    }

    protected void updatePriceRateListView()
    {
        priceRateListView.modelChanging();
        priceRateListView.setList( Arrays.asList( new Integer[ priceRateList.size() ] ) );
        priceRateListView.modelChanged();
    }

    public final void handleSubmitClicked()
    {
        boolean isRejected = false;

        if( nameField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

/*
        if( priceRateList.size() == 0 )
        {
            error( "Please add at least one Price Rate." );

            isRejected = true;
        }
*/

        if( isInvalidPriceRate() || isHasDuplicatePriceRate() )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }
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
        for( int i = 0; i < priceRateList.size() - 1; i++ )
        {
            final PriceRate priceRate = priceRateList.get( i );

            for( int j = i + 1; j < priceRateList.size(); j++ )
            {
                final PriceRate priceRateOther = priceRateList.get( j );

                if( isEqualValue( priceRate, priceRateOther ) )
                {
                    error( getString( DUPLICATE_ENTRY ) );

                    return true;
                }
            }
        }

        return false;
    }

    protected boolean isEqualValue( PriceRate priceRate, PriceRate priceRateOther )
    {
        return priceRate.projectRole().get().equals( priceRateOther.projectRole().get() ) &&
               priceRate.priceRateType().get().equals( priceRateOther.priceRateType().get() ) &&
               priceRate.amount().get().equals( priceRateOther.amount().get() );
    }

    protected void bindPropertyModel( IModel iModel )
    {
        nameField.setModel( new NameModel( iModel ) );
        currencyChoice.setModel( new CustomCompositeModel( iModel, "currency" ) );
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
}
