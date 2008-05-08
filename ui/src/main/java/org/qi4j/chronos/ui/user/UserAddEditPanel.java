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
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePanel;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.SystemRoleChoiceRenderer;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.common.model.CustomCompositeModel;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
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
        genderChoice =
            new SimpleDropDownChoice( "genderChoice", Arrays.asList( GenderType.values() ), true );

        IChoiceRenderer renderer = new SystemRoleChoiceRenderer();
        List<SystemRole> selected = getSelectedRoleChoices();
        List<SystemRole> choices = getAvailableRoleChoices();

        rolePalette = new Palette( "rolePalette", new Model( (Serializable) selected ),
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

    private List<SystemRole> getSelectedRoleChoices()
    {
        Iterator<SystemRole> roleIterator = getInitSelectedRoleList();
        List<SystemRole> resultList = new ArrayList<SystemRole>();

        while( roleIterator.hasNext() )
        {
            resultList.add( roleIterator.next() );
        }
        return resultList;
    }

    private List<SystemRole> getAvailableRoleChoices()
    {
        return ChronosSession.get().getSystemRoleService().findAllStaffSystemRole();
    }

    public List<SystemRole> getSelectedRoleList()
    {
        Iterator<SystemRole> selectedIterator = rolePalette.getSelectedChoices();

        List<SystemRole> SystemRoleList = new ArrayList<SystemRole>();

        while( selectedIterator.hasNext() )
        {
            SystemRoleList.add( selectedIterator.next() );
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

    public void bindPropertyModel( final IModel iModel )
    {
        firstNameField.setModel( new CustomCompositeModel( iModel, "firstName" ) );
        lastNameField.setModel( new CustomCompositeModel( iModel, "lastName" ) );
        genderChoice.setModel( new CustomCompositeModel( iModel, "gender" ) );

        if( null == genderChoice.getChoice() )
        {
            genderChoice.setChoice( GenderType.MALE );
        }

        IModel loginModel = new CustomCompositeModel( iModel, "login" );
        loginUserPanel.bindPropertyModel( loginModel );
    }

    public void assignFieldValueToUser( User user )
    {
        user.firstName().set( firstNameField.getText() );
        user.lastName().set( lastNameField.getText() );

//        GenderType genderType = GenderType.toEnum( genderChoice.getChoiceAsString() );
//        user.gender().set( genderType );

        if( !isHideRolePalette )
        {
            List<SystemRole> roleLists = getSelectedRoleList();
            user.systemRoles().addAll( roleLists );
        }

//        loginUserPanel.assignFieldValueToLogin( user );
    }

    public void assignUserToFieldValue( User user )
    {
        firstNameField.setText( user.firstName().get() );
        lastNameField.setText( user.lastName().get() );

//        genderChoice.setChoice( user.gender().get() );

//        loginUserPanel.assignLoginToFieldValue( user );

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
