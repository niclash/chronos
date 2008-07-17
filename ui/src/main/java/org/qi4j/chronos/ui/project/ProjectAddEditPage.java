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
package org.qi4j.chronos.ui.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.ui.common.FullNameChoiceRenderer;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.NameChoiceRenderer;
import org.qi4j.chronos.ui.common.SimpleDateField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.common.model.CustomCompositeModel;
import org.qi4j.chronos.ui.common.model.NameModel;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;

public abstract class ProjectAddEditPage extends AddEditBasePage
{
    protected MaxLengthTextField projectNameField;
    protected MaxLengthTextField formalReferenceField;
    protected SimpleDropDownChoice statusChoice;
    protected SimpleDropDownChoice primaryContactChoice;
    protected SimpleDropDownChoice customerChoice;
    protected SimpleDropDownChoice priceRateScheduleChoice;
    protected Palette contactPalette;
    protected SimpleDateField estimateStartDate;
    protected SimpleDateField estimateEndDate;
    protected SimpleDateField actualStartDate;
    protected SimpleDateField actualEndDate;
    private WebMarkupContainer actualDateContainer;
    private static final IChoiceRenderer nameChoiceRender = new NameChoiceRenderer();
    private static final IChoiceRenderer fullNameChoiceRender = new FullNameChoiceRenderer();
    protected List<Customer> availableCustomers;
    protected List<PriceRateSchedule> availablePriceRateSchedules;
    protected List<ContactPerson> selectedContactPersons;
    protected List<ContactPerson> availableContactPersons;

    public ProjectAddEditPage( Page basePage, final IModel iModel )
    {
        super( basePage, iModel );
    }

    public void initComponent( final Form form )
    {
        availableCustomers = new ArrayList<Customer>( getAccount().customers() );
        customerChoice = new SimpleDropDownChoice( "customerChoice", availableCustomers, nameChoiceRender )
        {
            @Override protected void onSelectionChanged( Object newSelection )
            {
                handleProjectOwnerChanged( form );
            }

            @Override protected boolean wantOnSelectionChangedNotifications()
            {
                return true;
            }
        };

        availablePriceRateSchedules =
            new ArrayList<PriceRateSchedule>( availableCustomers.get( 0 ).priceRateSchedules() );
        priceRateScheduleChoice =
            new SimpleDropDownChoice( "priceRateScheduleChoice", availablePriceRateSchedules, nameChoiceRender );

        projectNameField = new MaxLengthTextField( "projectName", "Project Name", Project.PROJECT_NAME_LEN );
        formalReferenceField =
            new MaxLengthTextField( "projectFormalReference",
                                    "Project Formal Reference", Project.PROJECT_FORMAL_REFERENCE_LEN );

        statusChoice = new SimpleDropDownChoice( "statusChoice", Arrays.asList( ProjectStatusEnum.values() ), true )
        {
            @Override protected void onSelectionChanged( Object newSelection )
            {
                handleStatusChanged( (ProjectStatusEnum) newSelection );
            }

            @Override protected boolean wantOnSelectionChangedNotifications()
            {
                return true;
            }
        };

        newOrReInitContactPalette( form );

        primaryContactChoice =
            new SimpleDropDownChoice(
                "primaryContactChoice", getAvailableContactPersonChoices(), fullNameChoiceRender );

        estimateStartDate = new SimpleDateField( "estimateStartDate" );
        estimateEndDate = new SimpleDateField( "estimateEndDate" );
        actualDateContainer = new WebMarkupContainer( "actualDateContainer" );
        actualDateContainer.setOutputMarkupId( true );
        actualStartDate = new SimpleDateField( "actualStartDate" );
        actualEndDate = new SimpleDateField( "actualEndDate" );
        actualDateContainer.add( actualStartDate );
        actualDateContainer.add( actualEndDate );
        actualDateContainer.setVisible( false );

        form.add( priceRateScheduleChoice );
        form.add( customerChoice );
        form.add( projectNameField );
        form.add( formalReferenceField );
        form.add( statusChoice );
        form.add( primaryContactChoice );
        form.add( estimateStartDate );
        form.add( estimateEndDate );
        form.add( actualDateContainer );
    }

    private void handleProjectOwnerChanged( Form form )
    {
        primaryContactChoice.modelChanging();
        primaryContactChoice.setNewChoices( getAvailableContactPersonChoices() );
        primaryContactChoice.modelChanged();
        priceRateScheduleChoice.modelChanging();
        priceRateScheduleChoice.setNewChoices( getAvailablePriceRateScheduleChoice() );
        priceRateScheduleChoice.modelChanged();
        newOrReInitContactPalette( form );
    }

    private void newOrReInitContactPalette( Form form )
    {
        form.modelChanging();
        if( contactPalette != null )
        {
            form.remove( contactPalette );
        }
        selectedContactPersons = getSelectedContactPersonChoices();
        availableContactPersons = getAvailableContactPersonChoices();

        if( !availableContactPersons.containsAll( selectedContactPersons ) )
        {
            selectedContactPersons.clear();
        }

        contactPalette =
            new Palette( "contactPalette", new Model( (Serializable) selectedContactPersons ),
                         new Model( (Serializable) availableContactPersons ), fullNameChoiceRender, 4, false );
        form.add( contactPalette );
        form.modelChanged();
    }

    protected List<ContactPerson> getSelectedContactPersonList()
    {
        Iterator<ContactPerson> contactPersonIterator = contactPalette.getSelectedChoices();
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();

        while( contactPersonIterator.hasNext() )
        {
            contactPersonList.add( contactPersonIterator.next() );
        }
        return contactPersonList;
    }

    private List<ContactPerson> getSelectedContactPersonChoices()
    {
        Iterator<ContactPerson> contactPersonIterator = ProjectAddEditPage.this.getInitSelectedContactPersonList();
        List<ContactPerson> selectedContactPerson = new ArrayList<ContactPerson>();

        while( contactPersonIterator.hasNext() )
        {
            selectedContactPerson.add( contactPersonIterator.next() );
        }
        return selectedContactPerson;
    }

    private List<ContactPerson> getAvailableContactPersonChoices()
    {
        final Customer customer = (Customer) customerChoice.getModelObject();
        return new ArrayList<ContactPerson>( customer.contactPersons() );
    }

    private List<PriceRateSchedule> getAvailablePriceRateScheduleChoice()
    {
        final Customer customer = (Customer) customerChoice.getModelObject();
        return new ArrayList<PriceRateSchedule>( customer.priceRateSchedules() );
    }

    private void handleStatusChanged( ProjectStatusEnum projectStatusEnum )
    {
        if( projectStatusEnum == ProjectStatusEnum.CLOSED )
        {
            actualDateContainer.setVisible( true );
            final IModel iModel = ProjectAddEditPage.this.getDefaultModel();
            final CustomCompositeModel actualModel = new CustomCompositeModel( iModel, "actualTime" );
            actualStartDate.setModel( new CustomCompositeModel( actualModel, "startTime" ) );
            actualEndDate.setModel( new CustomCompositeModel( actualModel, "endTime" ) );
        }
        else
        {
            actualDateContainer.setVisible( false );
        }
    }

    protected void bindPropertyModel( IModel iModel )
    {
        projectNameField.setModel( new NameModel( iModel ) );
        formalReferenceField.setModel( new CustomCompositeModel( iModel, "reference" ) );
        statusChoice.setModel( new CustomCompositeModel( iModel, "projectStatus" ) );
        customerChoice.setModel( new CustomCompositeModel( iModel, "customer" ) );
        priceRateScheduleChoice.setModel( new CustomCompositeModel( iModel, "priceRateSchedule" ) );

        CustomCompositeModel estimateModel = new CustomCompositeModel( iModel, "estimateTime" );
        estimateStartDate.setModel( new CustomCompositeModel( estimateModel, "startTime" ) );
        estimateEndDate.setModel( new CustomCompositeModel( estimateModel, "endTime" ) );

        // TODO kamil: proper binding actualStartDate and actualEndDate
        if( statusChoice.getModelObject() == ProjectStatusEnum.CLOSED )
        {
            actualDateContainer.setVisible( true );
            CustomCompositeModel actualModel = new CustomCompositeModel( iModel, "actualTime" );
            actualStartDate.setModel( new CustomCompositeModel( actualModel, "startTime" ) );
            actualEndDate.setModel( new CustomCompositeModel( actualModel, "endTime" ) );
        }
    }

    protected Customer getCustomer()
    {
        return ( (Project) getDefaultModelObject() ).customer().get();
    }

/*
    public void handleSubmitClicked()
    {
        boolean isRejected = false;

        if( projectNameField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( formalReferenceField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( ValidatorUtil.isAfter( estimateStartDate.getDate(), estimateEndDate.getDate(),
                                   "Start Date(Est.)", "End Date(Est.)", this ) )
        {
            isRejected = true;
        }

        if( statusChoice.getChoice() == ProjectStatusEnum.CLOSED )
        {
            if( ValidatorUtil.isAfter( actualStartDate.getDate(), actualEndDate.getDate(),
                                       "Start Date(Act.)", "End Date(Act.)", this ) )
            {
                isRejected = true;
            }
        }

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }
*/

//    public abstract void onSubmitting();

    public abstract Iterator<ContactPerson> getInitSelectedContactPersonList();
}
