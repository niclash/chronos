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
import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.pricerate.PriceRateOptionPanel;
import org.qi4j.chronos.ui.util.ListUtil;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class ProjectAssigneeAddEditPage extends AddEditBasePage<ProjectAssignee>
{
    private static final long serialVersionUID = 1L;

    private PriceRateOptionPanel priceRateOptionPanel;

    public ProjectAssigneeAddEditPage( Page basePage, IModel<ProjectAssignee> projectAssignee )
    {
        super( basePage, projectAssignee );
    }

    public void initComponent( Form<ProjectAssignee> form )
    {
        DropDownChoice<IModel<Staff>> staffChoice = new DropDownChoice<IModel<Staff>>( "staff", ListUtil.getStaffDelegator( getAccount() ) );

        CheckBox isLeadCheckBox = new CheckBox( "isLead" );

        priceRateOptionPanel = new PriceRateOptionPanel( "priceRateOptionPanel" )
        {
            private static final long serialVersionUID = 1L;

            public List<PriceRate> getAvailablePriceRates()
            {
                return ProjectAssigneeAddEditPage.this.getAvailablePriceRates();
            }

            public PriceRateSchedule getPriceRateSchedule()
            {
                return getProject().priceRateSchedule().get();
            }
        };

        form.add( staffChoice );
        form.add( isLeadCheckBox );
        form.add( priceRateOptionPanel );
    }

    public void handleSubmitClicked( IModel<ProjectAssignee> projectAssignee )
    {
        onsubmitting( projectAssignee );
    }

    public abstract void onsubmitting( IModel<ProjectAssignee> projectAssignee );

    public abstract List<PriceRate> getAvailablePriceRates();

    public abstract Project getProject();
}
