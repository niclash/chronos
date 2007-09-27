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

import java.util.List;
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.NumberTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.projectrole.ProjectRoleDelegator;
import org.qi4j.chronos.ui.util.ListUtil;

public abstract class PriceRateAddEditPage extends AddEditBasePage
{
    private NumberTextField amountField;
    private SimpleDropDownChoice currencyChoice;
    private SimpleDropDownChoice roleChoice;
    private SimpleDropDownChoice priceRateTypeChoice;

    public PriceRateAddEditPage( BasePage goBackPage )
    {
        super( goBackPage );
    }

    public final void initComponent( Form form )
    {
        amountField = new NumberTextField( "amount", "Amount" );

        List<String> priceRatyeTypeList = ListUtil.getPriceRateTypeList();
        List<ProjectRoleDelegator> roleList = ListUtil.getProjectRoleDelegatorList( getAccount() );
        List<String> currencyList = ListUtil.getCurrencyList();

        priceRateTypeChoice = new SimpleDropDownChoice( "priceRateTypeChoice", priceRatyeTypeList, true );
        currencyChoice = new SimpleDropDownChoice( "currencyChoice", currencyList, true );
        roleChoice = new SimpleDropDownChoice( "roleChoice", roleList, true );

        form.add( amountField );
        form.add( priceRateTypeChoice );
        form.add( currencyChoice );
        form.add( roleChoice );
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
