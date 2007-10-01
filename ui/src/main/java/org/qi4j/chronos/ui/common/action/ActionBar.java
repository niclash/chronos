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
package org.qi4j.chronos.ui.common.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;

public class ActionBar extends Panel
{
    private ActionTable actionTable;

    private Map<String, Action> actionMap;

    private SimpleDropDownChoice actionChoices;

    private Button goButton;

    public ActionBar()
    {
        super( "actionBar" );

        actionMap = new HashMap<String, Action>();

        initComponents();
    }

    private void initComponents()
    {
        final List<String> actionKeyList = getActionKeyList();
        actionChoices = new SimpleDropDownChoice( "actionChoices", actionKeyList, true );

        goButton = new Button( "goButton", new Model( "Go" ) )
        {
            public void onSubmit()
            {
                handleGo();
            }
        };

        goButton.setOutputMarkupId( true );

        goButton.setEnabled( false );

        add( actionChoices );
        add( goButton );
    }

    private void updateActionChoiceList()
    {
        final List<String> actionKeyList = getActionKeyList();

        actionChoices.setNewChoices( actionKeyList );
    }

    public void setActionTable( ActionTable actionTable )
    {
        this.actionTable = actionTable;
    }

    private List<String> getActionKeyList()
    {
        final List<String> actionKeyList = new ArrayList<String>( actionMap.keySet() );

        Collections.sort( actionKeyList );

        return actionKeyList;
    }

    public void addAction( Action action )
    {
        actionMap.put( action.getActionName(), action );

        updateActionChoiceList();
    }

    public void removeAction( Action action )
    {
        actionMap.remove( action.getActionName() );
    }

    private void handleGo()
    {
        if( !actionTable.isSelectedItems() )
        {
            return;
        }

        AbstractSortableDataProvider dataProvider = actionTable.getSelectedItemsDataProvider();

        String key = actionChoices.getModelObjectAsString();

        Action actionTarget = actionMap.get( key );

        actionTarget.performAction( dataProvider );
    }

    void setActionBarEnabled( boolean isEnabled, AjaxRequestTarget target )
    {
        goButton.setEnabled( isEnabled );

        if( target != null )
        {
            target.addComponent( goButton );
        }
    }
}
