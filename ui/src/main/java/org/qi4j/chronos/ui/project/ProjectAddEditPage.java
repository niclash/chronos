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
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectStatus;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.service.CustomerService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.SimpleDateField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.contactperson.ContactPersonDelegator;
import org.qi4j.chronos.ui.customer.CustomerDelegator;
import org.qi4j.chronos.ui.pricerate.PriceRateScheduleOptionPanel;
import org.qi4j.chronos.ui.util.ListUtil;
import org.qi4j.chronos.ui.util.ValidatorUtil;

public abstract class ProjectAddEditPage extends AddEditBasePage
{
    protected MaxLengthTextField projectNameField;
    protected MaxLengthTextField formalReferenceField;

    protected SimpleDropDownChoice<String> statusChoice;
    protected SimpleDropDownChoice<ContactPersonDelegator> primaryContactChoice;

    protected SimpleDropDownChoice<CustomerDelegator> customerChoice;

    protected Palette contactPalette;

    protected SimpleDateField estimateStartDate;
    protected SimpleDateField estimateEndDate;

    protected SimpleDateField actualStartDate;
    protected SimpleDateField actualEndDate;

    private WebMarkupContainer actualDateContainer;

    private PriceRateScheduleOptionPanel priceRateScheduleOptionPanel;

    private Form form;

    public ProjectAddEditPage( Page basePage )
    {
        super( basePage );
    }

    public void initComponent( Form form )
    {
        this.form = form;

        customerChoice = new SimpleDropDownChoice<CustomerDelegator>( "customerChoice", getProjectOwnerList(), true )
        {
            protected void onSelectionChanged( Object newSelection )
            {
                handleProjectOwnerChanged();
            }

            protected boolean wantOnSelectionChangedNotifications()
            {
                return true;
            }
        };

        priceRateScheduleOptionPanel = new PriceRateScheduleOptionPanel( "priceRateScheduleOptionPanel" )
        {
            public List<PriceRateScheduleComposite> getAvailablePriceRateSchedule()
            {
                return ProjectAddEditPage.this.getAvailablePriceRateScheduleChoice();
            }

            public AccountEntityComposite getAccount()
            {
                return ProjectAddEditPage.this.getAccount();
            }
        };

        projectNameField = new MaxLengthTextField( "projectName", "Project Name", Project.PROJECT_NAME_LEN );
        formalReferenceField = new MaxLengthTextField( "projectFormalReference", "Project Formal Reference", Project.PROJECT_FORMAL_REFERENCE_LEN );

        statusChoice = new SimpleDropDownChoice<String>( "statusChoice", ListUtil.getProjectStatusList(), true )
        {

            protected void onSelectionChanged( Object newSelection )
            {
                handleStatusChanged();
            }

            protected boolean wantOnSelectionChangedNotifications()
            {
                return true;
            }
        };

        newOrReInitContactPalette();

        primaryContactChoice = new SimpleDropDownChoice<ContactPersonDelegator>( "primaryContactChoice", getAvailableContactPersonChoices(), true );

        estimateStartDate = new SimpleDateField( "estimateStartDate" );
        estimateEndDate = new SimpleDateField( "estimateEndDate" );

        actualDateContainer = new WebMarkupContainer( "actualDateContainer" );
        actualDateContainer.setOutputMarkupId( true );

        actualStartDate = new SimpleDateField( "actualStartDate" );
        actualEndDate = new SimpleDateField( "actualEndDate" );

        actualDateContainer.add( actualStartDate );
        actualDateContainer.add( actualEndDate );

        form.add( priceRateScheduleOptionPanel );
        form.add( customerChoice );
        form.add( projectNameField );
        form.add( formalReferenceField );
        form.add( statusChoice );
        form.add( primaryContactChoice );
        form.add( estimateStartDate );
        form.add( estimateEndDate );
        form.add( actualDateContainer );

        actualDateContainer.setVisible( false );
    }

    private void handleProjectOwnerChanged()
    {
        newOrReInitContactPalette();

        primaryContactChoice.setNewChoices( getAvailableContactPersonChoices() );

        setResponsePage( this );
    }

    private void newOrReInitContactPalette()
    {
        if( contactPalette != null )
        {
            form.remove( contactPalette );
        }

        IChoiceRenderer renderer = new ChoiceRenderer( "fullname", "fullname" );

        List<ContactPersonDelegator> selecteds = getSelectedContactPersonChoices();
        List<ContactPersonDelegator> choices = getAvailableContactPersonChoices();

        contactPalette = new Palette( "contactPalette", new Model( (Serializable) selecteds ),
                                      new Model( (Serializable) choices ), renderer, 5, false );

        form.add( contactPalette );
    }

    private List<ContactPersonDelegator> getSelectedContactPersonChoices()
    {
        Iterator<ContactPersonEntityComposite> contactPersonIterator = getInitSelectedContactPersonList();

        List<ContactPersonEntityComposite> selectedContactPerson = new ArrayList<ContactPersonEntityComposite>();

        while( contactPersonIterator.hasNext() )
        {
            selectedContactPerson.add( contactPersonIterator.next() );
        }

        List<ContactPersonDelegator> systemRoleDelegators = constructContactPerson( selectedContactPerson );

        return systemRoleDelegators;
    }

    private List<ContactPersonDelegator> getAvailableContactPersonChoices()
    {
        CustomerEntityComposite projectOwner = getCustomerService().get( customerChoice.getChoice().getId() );

        List<ContactPersonEntityComposite> contactPersonList = getContactPersonService().findAll( projectOwner );

        List<ContactPersonDelegator> results = constructContactPerson( contactPersonList );

        return results;
    }

    private List<ContactPersonDelegator> constructContactPerson( List<ContactPersonEntityComposite> contacts )
    {
        List<ContactPersonDelegator> returns = new ArrayList<ContactPersonDelegator>();

        for( ContactPersonEntityComposite contactPerson : contacts )
        {
            returns.add( new ContactPersonDelegator( contactPerson ) );
        }

        return returns;
    }

    private ContactPersonService getContactPersonService()
    {
        return ChronosWebApp.getServices().getContactPersonService();
    }

    private ContactPersonEntityComposite getPrimaryContactPerson()
    {
        ContactPersonDelegator selected = primaryContactChoice.getChoice();

        if( selected != null )
        {
            return getContactPersonService().get( selected.getId() );
        }
        else
        {
            return null;
        }
    }

    private List<ContactPersonEntityComposite> getSelectedContactPersons()
    {
        Iterator<ContactPersonDelegator> selectedIter = contactPalette.getSelectedChoices();

        List<ContactPersonEntityComposite> resultList = new ArrayList<ContactPersonEntityComposite>();

        while( selectedIter.hasNext() )
        {
            resultList.add( getContactPersonService().get( selectedIter.next().getId() ) );
        }

        return resultList;
    }

    private void handleStatusChanged()
    {
        ProjectStatus projectStatus = getSelectedProjectStatus();

        if( projectStatus == ProjectStatus.CLOSED )
        {
            actualDateContainer.setVisible( true );
        }
        else
        {
            actualDateContainer.setVisible( false );
        }

        setResponsePage( this );
    }

    private ProjectStatus getSelectedProjectStatus()
    {
        String choice = statusChoice.getChoice();

        ProjectStatus projectStatus = ProjectStatus.valueOf( choice );

        return projectStatus;
    }

    private List<CustomerDelegator> getProjectOwnerList()
    {
        List<CustomerDelegator> delegatorList = new ArrayList<CustomerDelegator>();

        List<CustomerEntityComposite> customers = getCustomerService().findAll( getAccount() );

        for( CustomerEntityComposite customer : customers )
        {
            delegatorList.add( new CustomerDelegator( customer ) );
        }

        return delegatorList;
    }

    protected void assignFieldValuesToProject( ProjectEntityComposite project )
    {
        project.setName( projectNameField.getText() );
        project.setReference( formalReferenceField.getText() );

        project.setProjectStatus( getSelectedProjectStatus() );

        CustomerEntityComposite customer = getCustomerService().get( customerChoice.getChoice().getId() );

        project.setCustomer( customer );

        project.setPrimaryContactPerson( null );

        ContactPersonEntityComposite primaryContactPerson = getPrimaryContactPerson();

        if( primaryContactPerson != null )
        {
            project.setPrimaryContactPerson( primaryContactPerson );
        }

        project.removeAllContactPerson();

        List<ContactPersonEntityComposite> selectedContacts = getSelectedContactPersons();

        for( ContactPersonEntityComposite contactPerson : selectedContacts )
        {
            project.addContactPerson( contactPerson );
        }

        project.getEstimateTime().setStartTime( estimateStartDate.getDate() );
        project.getEstimateTime().setEndTime( estimateEndDate.getDate() );

        project.getActualTime().setStartTime( null );
        project.getActualTime().setEndTime( null );

        if( project.getProjectStatus() == ProjectStatus.CLOSED )
        {
            project.getActualTime().setStartTime( actualStartDate.getDate() );
            project.getActualTime().setEndTime( actualEndDate.getDate() );
        }

        project.setPriceRateSchedule( priceRateScheduleOptionPanel.getPriceRateSchedule() );
    }

    protected void assignProjectToFieldValues( Project project )
    {
        projectNameField.setText( project.getName() );

        formalReferenceField.setText( project.getReference() );

        statusChoice.setChoice( project.getProjectStatus().toString() );

        if( project.getProjectStatus() == ProjectStatus.CLOSED )
        {
            actualDateContainer.setVisible( true );
        }

        customerChoice.setChoice( new CustomerDelegator( project.getCustomer() ) );

        primaryContactChoice.setNewChoices( getAvailableContactPersonChoices() );

        if( project.getPrimaryContactPerson() != null )
        {
            primaryContactChoice.setChoice( new ContactPersonDelegator( project.getPrimaryContactPerson() ) );
        }

        estimateStartDate.setDate( project.getEstimateTime().getStartTime() );
        estimateEndDate.setDate( project.getEstimateTime().getEndTime() );

        actualStartDate.setDate( project.getActualTime().getStartTime() );
        actualEndDate.setDate( project.getActualTime().getEndTime() );

        //re-initilizae contact and priceRateSchedule values to reflect the selected projectOwner.
        newOrReInitContactPalette();

        priceRateScheduleOptionPanel.setPriceRateSchedule( project.getPriceRateSchedule() );

        //TODO bp. move this to other place?
        customerChoice.setEnabled( false );
    }

    protected CustomerService getCustomerService()
    {
        return ChronosWebApp.getServices().getCustomerService();
    }

    public void handleSubmit()
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

        if( priceRateScheduleOptionPanel.checkIfNotValidated() )
        {
            isRejected = true;
        }

        if( ValidatorUtil.isAfterDate( estimateStartDate.getDate(), estimateEndDate.getDate(),
                                       "Start Date(Est.)", "End Date(Est.)", this ) )
        {
            isRejected = true;
        }

        if( getSelectedProjectStatus() == ProjectStatus.CLOSED )
        {
            if( ValidatorUtil.isAfterDate( actualStartDate.getDate(), actualEndDate.getDate(),
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

    public abstract void onSubmitting();

    public abstract Iterator<ContactPersonEntityComposite> getInitSelectedContactPersonList();

    public abstract List<PriceRateScheduleComposite> getAvailablePriceRateScheduleChoice();
}

