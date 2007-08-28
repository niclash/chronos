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
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.Model;
import org.qi4j.api.persistence.Identity;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.NavigatorBar;

public abstract class ActionTable<T extends Identity> extends Panel
{
    private final static String CHECKBOX_CLASS_NAME = "checkBoxClass";

    public final static int DEFAULT_ITEM_PER_PAGE = 10;

    private ActionBar actionBar;

    private List<String> selectedIds;

    private DataView dataView;

    private CheckBox allCheckBox;

    public ActionTable( String id )
    {
        this( id, new ActionBar() );
    }

    public ActionTable( String id, ActionBar actionBar )
    {
        super( id );

        this.actionBar = actionBar;

        this.actionBar.setActionTable( this );

        selectedIds = new ArrayList<String>();

        initComponents();
    }

    private void initComponents()
    {
        add( new ActionTableForm( "actionTableForm" ) );
    }

    public void setItemPerPage( int itemPerPage )
    {
        dataView.setItemsPerPage( itemPerPage );
    }

    void handleSelectionChanged()
    {
        allCheckBox.setEnabled( actionBar.isSubsetSelected() );
        allCheckBox.modelChanged();

        dataView.modelChanged();
    }

    private class ActionTableForm extends Form
    {
        public ActionTableForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            add( actionBar );

            allCheckBox = new CheckBox( "allCheckBox", new Model( false ) );

            final String script = getAllCheckBoxOnClickScript();

            allCheckBox.add( new AttributeModifier( "onClick", true, new Model( script ) ) );

            add( allCheckBox );

            final List<String> headerList = getTableHeaderList();

            Loop headers = new Loop( "headers", headerList.size() )
            {
                protected void populateItem( LoopItem item )
                {
                    final int index = item.getIteration();

                    String header = headerList.get( index );

                    item.add( new Label( "header", header ) );
                }
            };

            add( headers );

            AbstractSortableDataProvider<T> dataProvider = getDetachableDataProvider();

            dataView = new DataView( "dataView", dataProvider )
            {
                private static final long serialVersionUID = 1L;

                @SuppressWarnings( { "unchecked" } )
                @Override
                protected void populateItem( Item item )
                {
                    T obj = (T) item.getModelObject();

                    final String id = obj.getIdentity();

                    CheckBox checkBox = new CheckBox( "itemCheckBox", new CheckedModel( id ) );
                    checkBox.add( new AttributeModifier( "class", true, new Model( CHECKBOX_CLASS_NAME ) ) );

                    checkBox.setEnabled( actionBar.isSubsetSelected() );

                    item.add( checkBox );

                    ActionTable.this.populateItems( item, obj );

                    if( item.getIndex() % 2 == 0 )
                    {
                        item.add( new AttributeModifier( "class", true, new Model( "alt" ) ) );
                    }
                }
            };

            add( dataView );

            dataView.setItemsPerPage( DEFAULT_ITEM_PER_PAGE );

            add( new NavigatorBar( "navigator", dataView ) );
        }

        protected void delegateSubmit( IFormSubmittingComponent submittingButton )
        {
            actionBar.onSubmitting( submittingButton );
        }
    }

    private String getAllCheckBoxOnClickScript()
    {
        return "var e=getElementsByClassName('" + CHECKBOX_CLASS_NAME + "','input', document ); " + //
               "var i=0; " + //
               "for(i;i<e.length;i++) " + //
               "{ e[i].checked=this.checked; }";
    }

    public void dataViewModelChanged()
    {
        dataView.modelChanged();
    }

    private class CheckedModel extends AbstractCheckBoxModel
    {
        private final String id;

        public CheckedModel( String id )
        {
            this.id = id;
        }

        @Override
        public boolean isSelected()
        {
            return selectedIds.contains( id );
        }

        @Override
        public void select()
        {
            selectedIds.add( id );
        }

        @Override
        public void unselect()
        {
            selectedIds.remove( id );
        }
    }

    public void addAction( Action action )
    {
        actionBar.addAction( action );
    }

    public void removeAction( Action action )
    {
        actionBar.removeAction( action );
    }

    boolean isSelectedItems()
    {
        if( getDetachableDataProvider().size() == 0 )
        {
            error( "No available item!" );
            return false;
        }

        final AbstractSortableDataProvider dataProvider = getSelectedItemsDataProvider();

        if( dataProvider.size() == 0 )
        {
            error( "Please select a least one item!" );
            return false;
        }

        return true;
    }

    AbstractSortableDataProvider getSelectedItemsDataProvider()
    {
        if( actionBar.isSubsetSelected() )
        {
            return new SubSetSortableDataProvider( selectedIds )
            {
                public Identity load( String id )
                {
                    return getDetachableDataProvider().load( id );
                }
            };
        }
        else
        {
            return getDetachableDataProvider();
        }
    }

    public abstract AbstractSortableDataProvider<T> getDetachableDataProvider();

    public abstract void populateItems( Item item, T obj );

    public abstract List<String> getTableHeaderList();
}
