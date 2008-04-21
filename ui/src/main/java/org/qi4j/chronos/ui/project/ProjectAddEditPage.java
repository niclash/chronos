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
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.service.CustomerService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.SimpleDateField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.contactperson.ContactPersonDelegator;
import org.qi4j.chronos.ui.customer.CustomerDelegator;
import org.qi4j.chronos.ui.pricerate.PriceRateScheduleOptionPanel;
import org.qi4j.chronos.ui.util.ValidatorUtil;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.entity.association.Association;
import org.qi4j.entity.association.SetAssociation;
import org.qi4j.entity.Identity;

public abstract class ProjectAddEditPage extends AddEditBasePage
{
    protected MaxLengthTextField projectNameField;
    protected MaxLengthTextField formalReferenceField;

    protected SimpleDropDownChoice<ProjectStatusEnum> statusChoice;
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
            public List<PriceRateSchedule> getAvailablePriceRateSchedule()
            {
                return ProjectAddEditPage.this.getAvailablePriceRateScheduleChoice();
            }

            public Account getAccount()
            {
                return ProjectAddEditPage.this.getAccount();
            }
        };

        projectNameField = new MaxLengthTextField( "projectName", "Project Name", Project.PROJECT_NAME_LEN );
        formalReferenceField = new MaxLengthTextField( "projectFormalReference", "Project Formal Reference", Project.PROJECT_FORMAL_REFERENCE_LEN );

        statusChoice = new SimpleDropDownChoice<ProjectStatusEnum>( "statusChoice", Arrays.asList( ProjectStatusEnum.values() ), true )
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
        Iterator<ContactPerson> contactPersonIterator = getInitSelectedContactPersonList();

        List<ContactPerson> selectedContactPerson = new ArrayList<ContactPerson>();

        while( contactPersonIterator.hasNext() )
        {
            selectedContactPerson.add( contactPersonIterator.next() );
        }

        List<ContactPersonDelegator> systemRoleDelegators = constructContactPerson( selectedContactPerson );

        return systemRoleDelegators;
    }

    private List<ContactPersonDelegator> getAvailableContactPersonChoices()
    {
        // TODO
//        Customer projectOwner = getCustomerService().get( customerChoice.getChoice().getId() );
        Customer projectOwner = getCustomer();

        List<ContactPerson> contactPersonList = getContactPersonService().findAll( projectOwner );

        List<ContactPersonDelegator> results = constructContactPerson( contactPersonList );

        return results;
    }

    private List<ContactPersonDelegator> constructContactPerson( List<ContactPerson> contacts )
    {
        List<ContactPersonDelegator> returns = new ArrayList<ContactPersonDelegator>();

        for( ContactPerson contactPerson : contacts )
        {
            returns.add( new ContactPersonDelegator( contactPerson ) );
        }

        return returns;
    }

    private ContactPersonService getContactPersonService()
    {
        return ChronosWebApp.getServices().getContactPersonService();
    }

    private ContactPerson getPrimaryContactPerson()
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

    private List<ContactPerson> getSelectedContactPersons()
    {
        Iterator<ContactPersonDelegator> selectedIter = contactPalette.getSelectedChoices();

        List<ContactPerson> resultList = new ArrayList<ContactPerson>();

        while( selectedIter.hasNext() )
        {
            resultList.add( getContactPersonService().get( selectedIter.next().getId() ) );
        }

        return resultList;
    }

    private void handleStatusChanged()
    {
        ProjectStatusEnum projectStatus = statusChoice.getChoice();

        if( projectStatus == ProjectStatusEnum.CLOSED )
        {
            actualDateContainer.setVisible( true );
        }
        else
        {
            actualDateContainer.setVisible( false );
        }

        setResponsePage( this );
    }

    private List<CustomerDelegator> getProjectOwnerList()
    {
        List<CustomerDelegator> delegatorList = new ArrayList<CustomerDelegator>();

        // TODO migrate
//        List<Customer> customers = getCustomerService().findAll( getAccount() );
        List<Customer> customers = new ArrayList<Customer>();

        for( Customer customer : customers )
        {
            delegatorList.add( new CustomerDelegator( customer ) );
        }

        return delegatorList;
    }

    protected void assignFieldValuesToProject( Project project )
    {
        project.name().set( projectNameField.getText() );
        project.reference().set( formalReferenceField.getText() );

        project.projectStatus().set( statusChoice.getChoice() );

        // TODO migrate
//        Customer customer = getCustomerService().get( customerChoice.getChoice().getId() );
        Customer customer = getCustomer();

        project.customer().set( customer );

        project.primaryContactPerson().set( null );

        ContactPerson primaryContactPerson = getPrimaryContactPerson();

        if( primaryContactPerson != null )
        {
            project.primaryContactPerson().set( primaryContactPerson );
        }

        SetAssociation<ContactPerson> projectContacts = project.contactPersons();
        projectContacts.clear();

        List<ContactPerson> selectedContacts = getSelectedContactPersons();
        projectContacts.addAll( selectedContacts );

        TimeRange projectEstimateTime = project.estimateTime().get();
        projectEstimateTime.startTime().set( estimateStartDate.getDate() );
        projectEstimateTime.endTime().set( estimateEndDate.getDate() );

        TimeRange projectActualTime = project.actualTime().get();
        projectActualTime.startTime().set( null );
        projectActualTime.endTime().set( null );

        if( project.projectStatus().get() == ProjectStatusEnum.CLOSED )
        {
            projectActualTime.startTime().set( actualStartDate.getDate() );
            projectActualTime.endTime().set( actualEndDate.getDate() );
        }

        Association<PriceRateSchedule> projectPriceRateSchedule = project.priceRateSchedule();
        projectPriceRateSchedule.set( priceRateScheduleOptionPanel.getPriceRateSchedule() );
    }

    protected void assignProjectToFieldValues( Project project )
    {
        projectNameField.setText( project.name().get() );

        formalReferenceField.setText( project.reference().get() );

        ProjectStatusEnum projectStatus = project.projectStatus().get();
        statusChoice.setChoice( projectStatus );

        if( projectStatus == ProjectStatusEnum.CLOSED )
        {
            actualDateContainer.setVisible( true );
        }

        Customer projectCustomer = project.customer().get();
        customerChoice.setChoice( new CustomerDelegator( projectCustomer ) );

        primaryContactChoice.setNewChoices( getAvailableContactPersonChoices() );

        ContactPerson primaryContactPerson = project.primaryContactPerson().get();
        if( primaryContactPerson != null )
        {
            primaryContactChoice.setChoice( new ContactPersonDelegator( primaryContactPerson ) );
        }

        TimeRange projectEstimateTime = project.estimateTime().get();
        estimateStartDate.setDate( projectEstimateTime.startTime().get() );
        estimateEndDate.setDate( projectEstimateTime.startTime().get() );

        TimeRange projectActualTime = project.actualTime().get();
        actualStartDate.setDate( projectActualTime.startTime().get() );
        actualEndDate.setDate( projectActualTime.endTime().get() );

        //re-initilizae contact and priceRateSchedule values to reflect the selected projectOwner.
        newOrReInitContactPalette();

        priceRateScheduleOptionPanel.setPriceRateSchedule( project.priceRateSchedule().get() );

        //TODO bp. move this to other place?
        customerChoice.setEnabled( false );
    }

    /*
        protected CustomerService getCustomerService()
        {
            return ChronosWebApp.getServices().getCustomerService();
        }
    */
    protected Customer getCustomer()
    {
        for( Customer customer : getAccount().customers() )
        {
            if( customerChoice.getChoice().getId().equals( ( (Identity) customer).identity().get() ) )
            {
                return customer;
            }
        }

        return null;
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

    public abstract void onSubmitting();

    public abstract Iterator<ContactPerson> getInitSelectedContactPersonList();

    public abstract List<PriceRateSchedule> getAvailablePriceRateScheduleChoice();
}

