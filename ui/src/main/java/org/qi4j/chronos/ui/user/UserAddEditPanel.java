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
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.ui.common.SystemRoleChoiceRenderer;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;
import org.qi4j.library.general.model.GenderType;

public abstract class UserAddEditPanel<T extends User> extends Panel
{
    private static final long serialVersionUID = 1L;

    private Palette rolePalette;
    private boolean isHideRolePalette;

    public UserAddEditPanel( String id, IModel<T> user )
    {
        this( id, user, false );
    }

    public UserAddEditPanel( String id, IModel<T> user, boolean isHideRolePalette )
    {
        super( id );

        ChronosCompoundPropertyModel model = new ChronosCompoundPropertyModel( user );
        setDefaultModel( model );

        this.isHideRolePalette = isHideRolePalette;

        TextField firstNameField = new TextField( "firstName" );
        TextField lastNameField = new TextField( "lastName" );
        DropDownChoice genderChoice = new DropDownChoice( "gender", Arrays.asList( GenderType.values() ) );

        IChoiceRenderer renderer = new SystemRoleChoiceRenderer();

        final List<SystemRole> selected = getSelectedRoleChoices();
        final List<SystemRole> choices = getAvailableRoleChoices();

        rolePalette = new Palette( "rolePalette", new Model( (Serializable) selected ),
                                   new Model( (Serializable) choices ), renderer, 4, false );

//        userLoginPanel = getLoginUserAbstractPanel( "userLoginPanel" );
        WebMarkupContainer roleContainer = new WebMarkupContainer( "roleContainer" );
        roleContainer.add( rolePalette );
        roleContainer.setVisible( !isHideRolePalette );

        add( firstNameField );
        add( lastNameField );
        add( genderChoice );
        add( roleContainer );
//        add( userLoginPanel );
    }

    private List<SystemRole> getSelectedRoleChoices()
    {
        Iterator<SystemRole> roleIterator = UserAddEditPanel.this.getInitSelectedRoleList();
        List<SystemRole> resultList = new ArrayList<SystemRole>();

        while( roleIterator.hasNext() )
        {
            resultList.add( roleIterator.next() );
        }
        return resultList;
    }

    private List<SystemRole> getAvailableRoleChoices()
    {
//        return ChronosSession.get().getSystemRoleService().findAllStaffSystemRole();
        return Collections.EMPTY_LIST;
    }

    public List<SystemRole> getSelectedRoleList()
    {
        Iterator<SystemRole> selectedIterator = rolePalette.getSelectedChoices();
        List<SystemRole> systemRoleList = new ArrayList<SystemRole>();

        while( selectedIterator.hasNext() )
        {
            systemRoleList.add( selectedIterator.next() );
        }
        return systemRoleList;
    }

    public Palette getRolePalette()
    {
        return rolePalette;
    }


    public void validateProperty( StringBuilder strBuilder )
    {

        if( !isHideRolePalette && getSelectedRoleList().size() == 0 )
        {
            strBuilder.append( "Please make at least one system role is selected!\n" );
        }

//        userLoginPanel.validateProperty( strBuilder );
    }

    protected abstract Iterator<SystemRole> getInitSelectedRoleList();

//    public abstract AbstractUserLoginPanel getLoginUserAbstractPanel( String id );
}
