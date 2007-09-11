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
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.AddEditBasePanel;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.util.ListUtil;

public abstract class UserAddEditPanel extends AddEditBasePanel
{
    private MaxLengthTextField firstNameField;
    private MaxLengthTextField lastNameField;
    private SimpleDropDownChoice genderChoice;

    private Palette rolePalette;

    public UserAddEditPanel( String id )
    {
        super( id );

        firstNameField = new MaxLengthTextField( "firstNameField", "First Name", User.FIRST_NAME_LEN );
        lastNameField = new MaxLengthTextField( "lastNameField", "Last Name", User.LAST_NAME_LEN );

        List<String> genderTypeList = ListUtil.getGenderTypeList();

        genderChoice = new SimpleDropDownChoice( "genderChoice", genderTypeList, true );

        IChoiceRenderer renderer = new ChoiceRenderer( "roleName", "roleName" );

        List<SystemRoleDelegator> selecteds = getSelectedRoleChoices();
        List<SystemRoleDelegator> choices = getAvailableRoleChoices();

        rolePalette = new Palette( "rolePalette", new Model( (Serializable) selecteds ),
                                   new Model( (Serializable) choices ), renderer, 5, false );
        add( firstNameField );
        add( lastNameField );
        add( genderChoice );
        add( rolePalette );
    }

    public abstract List<SystemRoleEntityComposite> getInitSelectedRoleList();

    private List<SystemRoleDelegator> getSelectedRoleChoices()
    {
        List<SystemRoleEntityComposite> projectRoleLists = getInitSelectedRoleList();

        List<SystemRoleDelegator> systemRoleDelegators = constuctRoleDelegatorList( projectRoleLists );

        return systemRoleDelegators;
    }

    private List<SystemRoleDelegator> constuctRoleDelegatorList( List<SystemRoleEntityComposite> projectRoleLists )
    {
        List<SystemRoleDelegator> systemRoleDelegators = new ArrayList<SystemRoleDelegator>();

        for( SystemRoleEntityComposite role : projectRoleLists )
        {
            systemRoleDelegators.add( new SystemRoleDelegator( role.getIdentity(), role.getName() ) );
        }

        return systemRoleDelegators;
    }

    private List<SystemRoleDelegator> getAvailableRoleChoices()
    {
        List<SystemRoleEntityComposite> systemRoleList = ChronosWebApp.getServices().
            getSystemRoleService().findAllStaffSystemRole();

        List<SystemRoleDelegator> systemRoleDelegators = constuctRoleDelegatorList( systemRoleList );

        return systemRoleDelegators;
    }

    public List<SystemRoleEntityComposite> getSelectedRoleList()
    {
        Iterator<SystemRoleDelegator> selectedIterator = rolePalette.getSelectedChoices();

        List<SystemRoleEntityComposite> SystemRoleList = new ArrayList<SystemRoleEntityComposite>();

        while( selectedIterator.hasNext() )
        {
            SystemRoleDelegator delegator = selectedIterator.next();

            SystemRoleEntityComposite systemRole = ChronosWebApp.getServices().getSystemRoleService().get( delegator.getRoleId() );

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

        if( getSelectedRoleList().size() == 0 )
        {
            error( "Please make at least one system role is selected!" );
            isRejected = true;
        }

        return isRejected;
    }

    private class SystemRoleDelegator implements Serializable
    {
        private String roleId;
        private String roleName;

        public SystemRoleDelegator( String roleId, String roleName )
        {
            this.roleId = roleId;
            this.roleName = roleName;
        }

        public String getRoleId()
        {
            return roleId;
        }

        public void setRoleId( String roleId )
        {
            this.roleId = roleId;
        }

        public String getRoleName()
        {
            return roleName;
        }

        public void setRoleName( String roleName )
        {
            this.roleName = roleName;
        }
    }
}
