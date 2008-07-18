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
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;

class ActionBar extends Panel
{
    private static final long serialVersionUID = 1L;

    private static final String WICKET_ID_ACTION_BAR = "actionBar";
    private static final String WICKET_ID_GO_BUTTON = "goButton";
    private static final String WICKET_ID_ACTION_CHOICES = "actionChoices";
    private static final String WICKET_ID_YES_LINK = "yesLink";
    private static final String WICKET_ID_NO_LINK = "noLink";
    private static final String WICKET_ID_CONFIRM_MSG_LABEL = "confirmMsgLabel";
    private static final String WICKET_ID_CONFIRMATION_CONTAINER = "confirmationContainer";

    private Map<String, Action> actionMap;

    private SimpleDropDownChoice actionChoices;

    private Button goButton;

    private Label confirmMsgLabel;

    private WebMarkupContainer confirmationContainer;

    private ActionTable actionTable;

    ActionBar( ActionTable actionTable )
    {
        super( WICKET_ID_ACTION_BAR );

        this.actionTable = actionTable;

        actionMap = new HashMap<String, Action>();

        initComponents();
    }

    @SuppressWarnings( "unchecked" )
    private void initComponents()
    {
        final List<String> actionKeyList = getActionKeyList();
        actionChoices = new SimpleDropDownChoice( WICKET_ID_ACTION_CHOICES, actionKeyList, true );

        goButton = new Button( WICKET_ID_GO_BUTTON )
        {
            private static final long serialVersionUID = 1L;

            public void onSubmit()
            {
                handleGo();
            }
        };

        goButton.setOutputMarkupId( true );
        goButton.setEnabled( false );

        Link yesLink = newYesLink();

        Link noLink = newNoLink();

        confirmMsgLabel = new Label( WICKET_ID_CONFIRM_MSG_LABEL, "" );

        confirmationContainer = new WebMarkupContainer( WICKET_ID_CONFIRMATION_CONTAINER );

        confirmationContainer.setOutputMarkupId( true );
        confirmationContainer.setOutputMarkupPlaceholderTag( true );
        confirmationContainer.setVisible( false );

        confirmationContainer.add( yesLink );
        confirmationContainer.add( noLink );
        confirmationContainer.add( confirmMsgLabel );

        add( actionChoices );
        add( goButton );
        add( confirmationContainer );
    }

    private Link newNoLink()
    {
        return new Link( WICKET_ID_NO_LINK )
        {
            private static final long serialVersionUID = 1L;

            public void onClick()
            {
                handleNo();
            }
        };
    }

    private Link newYesLink()
    {
        return new Link( WICKET_ID_YES_LINK )
        {
            private static final long serialVersionUID = 1L;

            public void onClick()
            {
                handleYes();
            }
        };
    }

    private void handleNo()
    {
        updateConfirmationVisibility( null, false );
    }

    private void handleYes()
    {
        performAction();
    }

    @SuppressWarnings( "unchecked" )
    private void handleGo()
    {
        if( !actionTable.isSelectedItems() )
        {
            return;
        }

        Action action = getSelectedAction();

        if( action.isShowConfirmDialog() )
        {
            confirmMsgLabel.setDefaultModel( new Model( action.getConfirmMsg() ) );

            updateConfirmationVisibility( null, true );
        }
        else
        {
            performAction();
        }
    }

    @SuppressWarnings( "unchecked" )
    private void performAction()
    {
        Action action = getSelectedAction();

        AbstractSortableDataProvider dataProvider = actionTable.getSelectedItemsDataProvider();

        action.performAction( dataProvider );

        goButton.setEnabled( false );
        updateConfirmationVisibility( null, false );

        actionTable.resetCurrBatchData();
    }

    private Action getSelectedAction()
    {
        String key = actionChoices.getDefaultModelObjectAsString();

        return actionMap.get( key );
    }

    @SuppressWarnings( "unchecked" )
    private void updateActionChoiceList()
    {
        final List<String> actionKeyList = getActionKeyList();

        actionChoices.setNewChoices( actionKeyList );
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

    void setActionBarEnabled( boolean isEnabled, AjaxRequestTarget target )
    {
        goButton.setEnabled( isEnabled );

        if( target != null )
        {
            target.addComponent( goButton );
        }

        updateConfirmationVisibility( target, false );
    }

    private void updateConfirmationVisibility( AjaxRequestTarget target, boolean visible )
    {
        confirmationContainer.setVisible( visible );

        if( target != null )
        {
            target.addComponent( confirmationContainer );
        }
    }
}
