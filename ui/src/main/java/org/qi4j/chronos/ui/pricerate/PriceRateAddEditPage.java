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
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.qi4j.chronos.model.PriceRateType;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectRoleComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.common.NumberTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.projectrole.ProjectRoleDelegator;
import org.qi4j.chronos.ui.util.ListUtil;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class PriceRateAddEditPage extends AddEditBasePage
{
    private NumberTextField amountField;
    private SimpleDropDownChoice<ProjectRoleDelegator> projectRoleChoice;
    private SimpleDropDownChoice<PriceRateType> priceRateTypeChoice;

    private SubmitLink selectPriceRateLink;
    private WebMarkupContainer selectPriceRateContainer;

    public PriceRateAddEditPage( Page goBackPage )
    {
        super( goBackPage );
    }

    protected void hidePriceRateSelectionLink()
    {
        selectPriceRateContainer.setVisible( false );
    }

    public final void initComponent( Form form )
    {
        selectPriceRateLink = new SubmitLink( "selectPriceRateLink" )
        {
            public void onSubmit()
            {
                handleSelectPriceRate();
            }
        };

        selectPriceRateContainer = new WebMarkupContainer( "selectPriceRateContainer" );
        selectPriceRateContainer.add( selectPriceRateLink );

        amountField = new NumberTextField( "amount", "Amount" );

        List<PriceRateType> priceRatyeTypeList = Arrays.asList( PriceRateType.values() );
        List<ProjectRoleDelegator> roleList = ListUtil.getProjectRoleDelegatorList( getAccount() );

        priceRateTypeChoice = new SimpleDropDownChoice<PriceRateType>( "priceRateTypeChoice", priceRatyeTypeList, true );
        projectRoleChoice = new SimpleDropDownChoice<ProjectRoleDelegator>( "projectRoleChoice", roleList, true );

        form.add( selectPriceRateContainer );
        form.add( amountField );
        form.add( priceRateTypeChoice );
        form.add( projectRoleChoice );
    }

    private void handleSelectPriceRate()
    {
        PriceRateSelectionPage selectionPage = new PriceRateSelectionPage( this )
        {
            public void handleSelectedPriceRate( PriceRateComposite priceRate )
            {
                PriceRateAddEditPage.this.handleSelectedPriceRate( priceRate );
            }

            public PriceRateScheduleComposite getPriceRateSchedule()
            {
                return PriceRateAddEditPage.this.getPriceRateSchedule();
            }
        };

        setResponsePage( selectionPage );
    }

    private void handleSelectedPriceRate( PriceRateComposite priceRate )
    {
        assignPriceRateToFieldValue( priceRate );
    }

    protected void assignFieldValueToPriceRate( PriceRateComposite priceRate )
    {
        priceRate.amount().set( amountField.getLongValue() );
        priceRate.priceRateType().set( priceRateTypeChoice.getChoice() );
        priceRate.projectRole().set( getSelectedProjectRole() );
    }

    protected void assignPriceRateToFieldValue( PriceRateComposite priceRate )
    {
        amountField.setLongValue( priceRate.amount().get() );
        priceRateTypeChoice.setChoice( priceRate.priceRateType().get() );
        projectRoleChoice.setChoice( new ProjectRoleDelegator( priceRate.projectRole().get() ) );
    }

    private ProjectRoleComposite getSelectedProjectRole()
    {
        ProjectRoleComposite projectRole = ChronosWebApp.newInstance( ProjectRoleComposite.class );

        projectRole.name().set( projectRoleChoice.getChoice().getName() );

        return projectRole;
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

    public abstract PriceRateScheduleComposite getPriceRateSchedule();
}
