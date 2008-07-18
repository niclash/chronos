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
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
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

    private final Map<String, Action> actionMap;
    private final ActionTable actionTable;

    private final SimpleDropDownChoice<String> actionChoices;
    private final Button goButton;
    private final ActionConfirmationContainer confirmationContainer;

    ActionBar( ActionTable anActionTable )
    {
        super( WICKET_ID_ACTION_BAR );

        actionTable = anActionTable;
        actionMap = new HashMap<String, Action>();

        List<String> actions = new ArrayList<String>();
        actionChoices = new SimpleDropDownChoice<String>( WICKET_ID_ACTION_CHOICES, actions, true );
        add( actionChoices );

        confirmationContainer = new ActionConfirmationContainer();
        add( confirmationContainer );

        goButton = new ExecuteActionButton( confirmationContainer );
        add( goButton );
    }

    @SuppressWarnings( "unchecked" )
    private void performAction()
    {
        Action action = getSelectedAction();

        AbstractSortableDataProvider dataProvider = actionTable.getSelectedItemsDataProvider();

        action.performAction( dataProvider );

        goButton.setEnabled( false );
        confirmationContainer.setVisible( false );

        actionTable.resetCurrBatchData();
    }

    private Action getSelectedAction()
    {
        String key = actionChoices.getDefaultModelObjectAsString();
        return actionMap.get( key );
    }

    @SuppressWarnings( "unchecked" )
    final void addAction( Action action )
    {
        String actionName = action.getActionName();
        actionMap.put( actionName, action );

        List choices = actionChoices.getChoices();
        choices.add( actionName );
        Collections.sort( choices );
    }

    final void removeAction( Action action )
    {
        String actionName = action.getActionName();
        actionMap.remove( actionName );
    }

    final void setActionBarEnabled( boolean isEnabled, AjaxRequestTarget target )
    {
        goButton.setEnabled( isEnabled );
        confirmationContainer.setVisible( false );

        if( target != null )
        {
            target.addComponent( goButton );
            target.addComponent( confirmationContainer );
        }
    }

    private class ExecuteActionButton extends Button
    {
        private static final long serialVersionUID = 1L;

        private final ActionConfirmationContainer confirmationContainer;

        private ExecuteActionButton( ActionConfirmationContainer actionConfirmationContainer )
        {
            super( WICKET_ID_GO_BUTTON );
            confirmationContainer = actionConfirmationContainer;

            setOutputMarkupId( true );
            setEnabled( false );
        }

        @Override
        public final void onSubmit()
        {
            if( !actionTable.isSelectedItems() )
            {
                return;
            }

            Action action = getSelectedAction();
            if( action.isShowConfirmDialog() )
            {
                IModel<String> confirmMsg = action.getConfirmMsg();
                confirmationContainer.displayConfirmMessage( confirmMsg );
                confirmationContainer.setVisible( true );
            }
            else
            {
                performAction();
            }
        }
    }

    private class ActionConfirmationContainer extends WebMarkupContainer
    {
        private static final long serialVersionUID = 1L;

        private final Label confirmMsgLabel;

        private ActionConfirmationContainer()
        {
            super( WICKET_ID_CONFIRMATION_CONTAINER );

            setOutputMarkupId( true );
            setOutputMarkupPlaceholderTag( true );
            setVisible( false );

            confirmMsgLabel = new Label( WICKET_ID_CONFIRM_MSG_LABEL, "" );

            add( new ConfirmActionLink() );
            add( new CancelConfirmActionLink() );
            add( confirmMsgLabel );
        }

        private void displayConfirmMessage( IModel<String> aMessage )
        {
            confirmMsgLabel.setDefaultModel( aMessage );
        }

        private class ConfirmActionLink extends Link
        {
            private static final long serialVersionUID = 1L;

            public ConfirmActionLink()
            {
                super( WICKET_ID_YES_LINK );
            }

            @Override
            public final void onClick()
            {
                performAction();
            }
        }

        private class CancelConfirmActionLink extends Link
        {
            private static final long serialVersionUID = 1L;

            private CancelConfirmActionLink()
            {
                super( WICKET_ID_NO_LINK );
            }

            @Override
            public final void onClick()
            {
                MarkupContainer confirmationContainer = getParent();
                confirmationContainer.setVisible( false );
            }
        }
    }

}
