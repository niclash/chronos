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
package org.qi4j.chronos.ui.projectassignee;

import java.util.List;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.pricerate.PriceRateOptionPanel;
import org.qi4j.chronos.ui.staff.StaffDelegator;
import org.qi4j.chronos.ui.util.ListUtil;

public abstract class ProjectAssigneeAddEditPage extends AddEditBasePage
{
    private SimpleDropDownChoice<StaffDelegator> staffChoice;
    private CheckBox isLeadCheckBox;
    private PriceRateOptionPanel priceRateOptionPanel;

    public ProjectAssigneeAddEditPage( BasePage basePage )
    {
        super( basePage );
    }

    public void initComponent( Form form )
    {
        staffChoice = new SimpleDropDownChoice<StaffDelegator>( "staffChoice",
                                                                ListUtil.getStaffDelegator( getAccount() ), true );

        isLeadCheckBox = new CheckBox( "isLeadCheckBox", new Model( false ) );

        priceRateOptionPanel = new PriceRateOptionPanel( "priceRateOptionPanel" )
        {
            public List<PriceRateComposite> getAvailablePriceRates()
            {
                return ProjectAssigneeAddEditPage.this.getAvailablePriceRates();
            }

            public ProjectEntityComposite getProject()
            {
                return ProjectAssigneeAddEditPage.this.getProject();
            }
        };

        form.add( staffChoice );
        form.add( isLeadCheckBox );
        form.add( priceRateOptionPanel );
    }

    protected void assignFieldValueToProjectAssignee( ProjectAssigneeEntityComposite projectAssignee )
    {
        projectAssignee.setPriceRate( priceRateOptionPanel.getSelectedPriceRate() );
        projectAssignee.setStaff( getSelectedStaff() );

        projectAssignee.setLead( Boolean.parseBoolean( isLeadCheckBox.getModelObjectAsString() ) );
    }

    protected void assignProjectAssigneeToFieldValue( ProjectAssigneeEntityComposite projectAssignee )
    {
        priceRateOptionPanel.setSelectedPriceRate( projectAssignee.getPriceRate() );
        staffChoice.setChoice( new StaffDelegator( projectAssignee.getStaff() ) );
        isLeadCheckBox.setModel( new Model( projectAssignee.isLead() ) );

        staffChoice.setEnabled( false );
    }

    private StaffEntityComposite getSelectedStaff()
    {
        return ChronosWebApp.getServices().getStaffService().get( staffChoice.getChoice().getId() );
    }

    public void handleSubmit()
    {
        onsubmitting();
    }

    public abstract void onsubmitting();

    public abstract ProjectEntityComposite getProject();

    public abstract List<PriceRateComposite> getAvailablePriceRates();
}
