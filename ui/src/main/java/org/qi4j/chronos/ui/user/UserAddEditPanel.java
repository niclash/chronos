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
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.SystemRoleComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.AddEditBasePanel;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.systemrole.SystemRoleDelegator;
import org.qi4j.chronos.ui.util.ListUtil;
import org.qi4j.library.general.model.GenderType;

public abstract class UserAddEditPanel extends AddEditBasePanel
{
    private MaxLengthTextField firstNameField;
    private MaxLengthTextField lastNameField;
    private SimpleDropDownChoice genderChoice;

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

        List<String> genderTypeList = ListUtil.getGenderTypeList();

        genderChoice = new SimpleDropDownChoice( "genderChoice", genderTypeList, true );

        IChoiceRenderer renderer = new ChoiceRenderer( "roleName", "roleName" );

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
        Iterator<SystemRoleComposite> roleIterator = getInitSelectedRoleList();

        List<SystemRoleComposite> resultList = new ArrayList<SystemRoleComposite>();

        while( roleIterator.hasNext() )
        {
            resultList.add( roleIterator.next() );
        }

        List<SystemRoleDelegator> systemRoleDelegators = constuctRoleDelegatorList( resultList );

        return systemRoleDelegators;
    }

    private List<SystemRoleDelegator> constuctRoleDelegatorList( List<SystemRoleComposite> projectRoleLists )
    {
        List<SystemRoleDelegator> systemRoleDelegators = new ArrayList<SystemRoleDelegator>();

        for( SystemRoleComposite role : projectRoleLists )
        {
            systemRoleDelegators.add( new SystemRoleDelegator( role ) );
        }

        return systemRoleDelegators;
    }

    private List<SystemRoleDelegator> getAvailableRoleChoices()
    {
        List<SystemRoleComposite> systemRoleList = ChronosWebApp.getServices().
            getSystemRoleService().findAllStaffSystemRole();

        List<SystemRoleDelegator> systemRoleDelegators = constuctRoleDelegatorList( systemRoleList );

        return systemRoleDelegators;
    }

    public List<SystemRoleComposite> getSelectedRoleList()
    {
        Iterator<SystemRoleDelegator> selectedIterator = rolePalette.getSelectedChoices();

        List<SystemRoleComposite> SystemRoleList = new ArrayList<SystemRoleComposite>();

        while( selectedIterator.hasNext() )
        {
            SystemRoleDelegator delegator = selectedIterator.next();

            SystemRoleComposite systemRole = ChronosWebApp.newInstance( SystemRoleComposite.class );

            systemRole.setSystemRoleType( delegator.getSystemRoleType() );
            systemRole.setName( delegator.getSystemRoleName() );

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

    public SimpleDropDownChoice getGenderChoice()
    {
        return genderChoice;
    }

    public void assignFieldValueToUser( User user )
    {
        user.setFirstName( firstNameField.getText() );
        user.setLastName( lastNameField.getText() );

        GenderType genderType = GenderType.getGenderType( genderChoice.getChoiceAsString() );
        user.setGender( genderType );

        if( !isHideRolePalette )
        {
            List<SystemRoleComposite> roleLists = getSelectedRoleList();

            for( SystemRoleComposite role : roleLists )
            {
                user.addSystemRole( role );
            }
        }

        loginUserPanel.assignFieldValueToLogin( user.getLogin() );
    }

    public void assignUserToFieldValue( User user )
    {
        firstNameField.setText( user.getFirstName() );
        lastNameField.setText( user.getLastName() );

        genderChoice.setChoice( user.getGender().toString() );

        loginUserPanel.assignLoginToFieldValue( user.getLogin() );

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

    protected abstract Iterator<SystemRoleComposite> getInitSelectedRoleList();

    public abstract LoginUserAbstractPanel getLoginUserAbstractPanel( String id );
}
