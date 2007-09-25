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
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.NumberTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.util.ListUtil;

public abstract class PriceRateScheduleAddEditPage extends AddEditBasePage
{
    private MaxLengthTextField nameField;

    private List<SimpleDropDownChoice<String>> projectRoleChoiceList;
    private List<SimpleDropDownChoice<String>> projectRateTypeChoiceList;
    private List<SimpleDropDownChoice<String>> currencyChoiceList;
    private List<NumberTextField> amountFieldList;

    private ListView priceRateListView;

    private SimpleLink newPriceRateLink;

    //TODO remove static
    private static List<PriceRateComposite> priceRateList;

    public PriceRateScheduleAddEditPage( BasePage goBackPage )
    {
        super( goBackPage );
    }

    public final void initComponent( Form form )
    {

        newPriceRateLink = new SimpleLink( "newPriceRateLink", "New Price Rate" )
        {
            public void linkClicked()
            {
                handleNewPriceRate();
            }
        };

        priceRateList = getInitPriceRateList();

        priceRateListView = new ListView( "priceRateListView", Arrays.asList( new int[priceRateList.size()] ) )
        {
            protected void populateItem( final ListItem item )
            {
                SimpleDropDownChoice projectRoleChoice = new SimpleDropDownChoice<String>( "projectRoleChoice", ListUtil.getProjectRoleList( getAccount() ), true );
                SimpleDropDownChoice projectRateTypeChoice = new SimpleDropDownChoice<String>( "projectRateTypeChoice", ListUtil.getPriceRateTypeList(), true );
                SimpleDropDownChoice currencyChoice = new SimpleDropDownChoice<String>( "currencyChoice", ListUtil.getCurrencyList(), true );

                NumberTextField amountField = new NumberTextField( "amountField", "Amount" );

                SimpleLink deleteLink = new SimpleLink( "deleteLink", "Delete" )
                {
                    public void linkClicked()
                    {
                        removePriceRate( item.getIndex() );
                    }
                };

                item.add( projectRoleChoice );
                item.add( projectRateTypeChoice );
                item.add( currencyChoice );
                item.add( amountField );
                item.add( deleteLink );

                projectRoleChoiceList.add( projectRoleChoice );
                projectRateTypeChoiceList.add( projectRateTypeChoice );
                currencyChoiceList.add( currencyChoice );
                amountFieldList.add( amountField );
            }
        };

        nameField = new MaxLengthTextField( "name", "Name", PriceRateSchedule.NAME_LEN );

        form.add( newPriceRateLink );
        form.add( priceRateListView );
        form.add( nameField );
    }

    private void handleNewPriceRate()
    {
        //TODO
    }

    private void removePriceRate( int index )
    {
        priceRateList.remove( index );

        projectRoleChoiceList.clear();
        projectRateTypeChoiceList.clear();
        currencyChoiceList.clear();
        amountFieldList.clear();

        priceRateListView.setList( Arrays.asList( new int[priceRateList.size()] ) );
        priceRateListView.modelChanged();
    }

    public final void handleSubmit()
    {
        boolean isRejected = false;

        if( nameField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    protected void assignFieldValueToPriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        priceRateSchedule.setName( nameField.getText() );
    }

    protected void assignPriceRateScheduleToFieldValue( PriceRateSchedule priceRateSchedule )
    {
        nameField.setText( priceRateSchedule.getName() );
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

    public abstract AccountEntityComposite getAccount();

    public abstract void onSubmitting();
}
