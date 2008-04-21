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
package org.qi4j.chronos.ui.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePanel;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.systemrole.SystemRoleDelegator;
import org.qi4j.library.general.model.GenderType;

public abstract class UserAddEditPanel extends AddEditBasePanel
{
    private MaxLengthTextField firstNameField;
    private MaxLengthTextField lastNameField;
    private SimpleDropDownChoice<GenderType> genderChoice;

    private Palette rolePalette;

    private boolean isHideRolePalette;

    private LoginUserAbstractPanel loginUserPanel;

    private WebMarkupContainer roleContainer;

    public UserAddEditPanel( String id )
    {
        this( id, false );
    }

    public UserAddEditPanel( String id, boolean isHideRolePalette )
    {
        super( id );

        this.isHideRolePalette = isHideRolePalette;

        firstNameField = new MaxLengthTextField( "firstNameField", "First Name", User.FIRST_NAME_LEN );
        lastNameField = new MaxLengthTextField( "lastNameField", "Last Name", User.LAST_NAME_LEN );

        genderChoice = new SimpleDropDownChoice<GenderType>( "genderChoice", Arrays.asList( GenderType.values() ), true );

        IChoiceRenderer renderer = new ChoiceRenderer( "systemRoleName", "systemRoleName" );

        List<SystemRoleDelegator> selecteds = getSelectedRoleChoices();
        List<SystemRoleDelegator> choices = getAvailableRoleChoices();

        rolePalette = new Palette( "rolePalette", new Model( (Serializable) selecteds ),
                                   new Model( (Serializable) choices ), renderer, 5, false );

        loginUserPanel = getLoginUserAbstractPanel( "loginUserPanel" );

        roleContainer = new WebMarkupContainer( "roleContainer" );

        roleContainer.add( rolePalette );

        roleContainer.setVisible( !isHideRolePalette );

        add( firstNameField );
        add( lastNameField );
        add( genderChoice );
        add( roleContainer );

        add( loginUserPanel );
    }

    private List<SystemRoleDelegator> getSelectedRoleChoices()
    {
        Iterator<SystemRole> roleIterator = getInitSelectedRoleList();

        List<SystemRole> resultList = new ArrayList<SystemRole>();

        while( roleIterator.hasNext() )
        {
            resultList.add( roleIterator.next() );
        }

        List<SystemRoleDelegator> systemRoleDelegators = constuctRoleDelegatorList( resultList );

        return systemRoleDelegators;
    }

    private List<SystemRoleDelegator> constuctRoleDelegatorList( List<SystemRole> projectRoleLists )
    {
        List<SystemRoleDelegator> systemRoleDelegators = new ArrayList<SystemRoleDelegator>();

        for( SystemRole role : projectRoleLists )
        {
            systemRoleDelegators.add( new SystemRoleDelegator( role ) );
        }

        return systemRoleDelegators;
    }

    private List<SystemRoleDelegator> getAvailableRoleChoices()
    {
        // TODO kamil: make it proper
//        List<SystemRole> systemRoleList = ChronosWebApp.getServices().
//            getSystemRoleService().findAllStaffSystemRole();

        List<SystemRole> systemRoleList = ChronosSession.get().getSystemRoleService().findAllStaffSystemRole();
        List<SystemRoleDelegator> systemRoleDelegators = constuctRoleDelegatorList( systemRoleList );

        return systemRoleDelegators;
    }

    public List<SystemRole> getSelectedRoleList()
    {
        Iterator<SystemRoleDelegator> selectedIterator = rolePalette.getSelectedChoices();

        List<SystemRole> SystemRoleList = new ArrayList<SystemRole>();

        while( selectedIterator.hasNext() )
        {
            SystemRoleDelegator delegator = selectedIterator.next();

            // TODO kamil: make it proper
//            SystemRole systemRole = ChronosWebApp.newInstance( SystemRoleEntityComposite.class );
            SystemRole systemRole = ChronosSession.get().getSystemRoleService().getSystemRoleByName( delegator.getSystemRoleName() );

//            systemRole.systemRoleType().set( delegator.getSystemRoleType() );
//            systemRole.name().set( delegator.getSystemRoleName() );

            SystemRoleList.add( systemRole );
        }

        return SystemRoleList;
    }

    public Palette getRolePalette()
    {
        return rolePalette;
    }

    public MaxLengthTextField getFirstNameField()
    {
        return firstNameField;
    }

    public MaxLengthTextField getLastNameField()
    {
        return lastNameField;
    }

    public SimpleDropDownChoice<GenderType> getGenderChoice()
    {
        return genderChoice;
    }

    public void assignFieldValueToUser( User user )
    {
        user.firstName().set( firstNameField.getText() );
        user.lastName().set( lastNameField.getText() );

        GenderType genderType = GenderType.toEnum( genderChoice.getChoiceAsString() );
        user.gender().set( genderType );

        if( !isHideRolePalette )
        {
            List<SystemRole> roleLists = getSelectedRoleList();
            user.systemRoles().addAll( roleLists );
        }

        loginUserPanel.assignFieldValueToLogin( user );
    }

    public void assignUserToFieldValue( User user )
    {
        firstNameField.setText( user.firstName().get() );
        lastNameField.setText( user.lastName().get() );

        genderChoice.setChoice( user.gender().get() );

        loginUserPanel.assignLoginToFieldValue( user );

        //skip the rolePalette, as it is already done in getInitSelectedRoleList();
    }

    public boolean checkIsNotValidated()
    {
        boolean isRejected = false;

        if( firstNameField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( lastNameField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( !isHideRolePalette && getSelectedRoleList().size() == 0 )
        {
            error( "Please make at least one system role is selected!" );
            isRejected = true;
        }

        if( loginUserPanel.checkIsNotValidated() )
        {
            isRejected = true;
        }

        return isRejected;
    }

    protected abstract Iterator<SystemRole> getInitSelectedRoleList();

    public abstract LoginUserAbstractPanel getLoginUserAbstractPanel( String id );
}
