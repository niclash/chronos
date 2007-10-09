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

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.Page;
import org.qi4j.chronos.model.PriceRateType;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.NumberTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.projectrole.ProjectRoleDelegator;
import org.qi4j.chronos.ui.util.ListUtil;
import org.qi4j.chronos.util.CurrencyUtil;

public abstract class PriceRateAddEditPage extends AddEditBasePage
{
    private NumberTextField amountField;
    private SimpleDropDownChoice<ProjectRoleDelegator> projectRoleChoice;
    private SimpleDropDownChoice<PriceRateType> priceRateTypeChoice;

    public PriceRateAddEditPage( Page goBackPage )
    {
        super( goBackPage );
    }

    public final void initComponent( Form form )
    {
        amountField = new NumberTextField( "amount", "Amount" );

        List<PriceRateType> priceRatyeTypeList = Arrays.asList( PriceRateType.values() );
        List<ProjectRoleDelegator> roleList = ListUtil.getProjectRoleDelegatorList( getAccount() );
        List<Currency> currencyList = CurrencyUtil.getCurrencyList();

        priceRateTypeChoice = new SimpleDropDownChoice<PriceRateType>( "priceRateTypeChoice", priceRatyeTypeList, true );
        projectRoleChoice = new SimpleDropDownChoice<ProjectRoleDelegator>( "projectRoleChoice", roleList, true );

        form.add( amountField );
        form.add( priceRateTypeChoice );
        form.add( projectRoleChoice );
    }

    protected void assignFieldValueToPriceRate( PriceRateComposite priceRate )
    {
        priceRate.setAmount( amountField.getLongValue() );
        priceRate.setPriceRateType( priceRateTypeChoice.getChoice() );
        priceRate.setProjectRole( getSelectedProjectRole() );
    }

    protected void assignPriceRateToFieldValue( PriceRateComposite priceRate )
    {
        amountField.setLongValue( priceRate.getAmount() );
        priceRateTypeChoice.setChoice( priceRate.getPriceRateType() );
        projectRoleChoice.setChoice( new ProjectRoleDelegator( priceRate.getProjectRole() ) );
    }

    private ProjectRoleEntityComposite getSelectedProjectRole()
    {
        return ChronosWebApp.getServices().getProjectRoleService().get( projectRoleChoice.getChoice().getId() );
    }

    public final void handleSubmit()
    {
        boolean isRejected = false;

        if( amountField.checkIsEmptyOrNotInteger() )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    public abstract void onSubmitting();
}
