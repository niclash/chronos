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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class ActionTable<T> extends Panel
{
    private final static String CHECKBOX_CLASS_NAME = "checkBoxClass";

    public final static int DEFAULT_ITEM_PER_PAGE = 10;

    private ActionBar actionBar;

    private List<String> selectedIds;

    private DataView dataView;

    private List<String> currBatchIds;
    private List<CheckBox> currBatchCheckBoxs;

    private Label totalSelectedLabel;

    private int totalItems;

    private WebMarkupContainer grandSelectAllContainer;
    private WebMarkupContainer grandSelectNoneContainer;

    private boolean isGrandAllSelected = false;

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

        currBatchIds = new ArrayList<String>();
        currBatchCheckBoxs = new ArrayList<CheckBox>();

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

    public void setActionBarVisible( boolean visible )
    {
        actionBar.setVisible( visible );
    }

    private class ActionTableForm extends Form
    {
        private AbstractSortableDataProvider<T> dataProvider;

        public ActionTableForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            add( actionBar );

            dataProvider = getDetachableDataProvider();

            totalItems = dataProvider.size();

            initGrandSelectAllComponents();
            initGrandSelectNoneComponents();
            initSelectAllOrNoneComponent();
            initDataViewComponent();
        }

        private void initDataViewComponent()
        {
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

            dataView = new DataView( "dataView", dataProvider )
            {
                @SuppressWarnings( { "unchecked" } )
                @Override
                protected void populateItem( Item item )
                {
                    if( item.getIndex() == 0 )
                    {
                        currBatchIds.clear();
                        currBatchCheckBoxs.clear();
                    }

                    T obj = (T) item.getModelObject();

                    final String id = dataProvider.getId( obj );

                    final CheckBox checkBox = new CheckBox( "itemCheckBox", new CheckedModel( id ) );
                    checkBox.add( new AttributeModifier( "class", true, new Model( CHECKBOX_CLASS_NAME ) ) );

                    checkBox.add( new AjaxFormComponentUpdatingBehavior( "onchange" )
                    {
                        @Override
                        protected void onUpdate( AjaxRequestTarget target )
                        {
                            handleItemCheckBoxChanged( checkBox, target );
                        }
                    } );

                    item.add( checkBox );

                    ActionTable.this.populateItems( item, obj );

                    if( item.getIndex() % 2 == 0 )
                    {
                        item.add( new AttributeModifier( "class", true, new Model( "alt" ) ) );
                    }

                    currBatchIds.add( id );
                    currBatchCheckBoxs.add( checkBox );
                }
            };

            add( dataView );

            dataView.setItemsPerPage( DEFAULT_ITEM_PER_PAGE );

            add( new ActionTableNavigatorBar( "navigator", dataView )
            {
                public void beforeItemPerPageChanged()
                {
                    selectedIds.clear();

                    updateCurrBatchAndGrandSelectVisibility();
                }

                public void beforeNextNagivation()
                {
                    updateCurrBatchAndGrandSelectVisibility();
                }
            } );
        }

        private void initSelectAllOrNoneComponent()
        {
            AjaxSubmitLink selectAllLink = new AjaxSubmitLink( "selectAllLink" )
            {
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    handleSelectAll( target, true );
                }
            };

            AjaxSubmitLink selectNoneLink = new AjaxSubmitLink( "selectNoneLink" )
            {
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    handleSelectAll( target, false );
                }
            };

            add( selectAllLink );
            add( selectNoneLink );
        }

        private void initGrandSelectAllComponents()
        {
            grandSelectAllContainer = new WebMarkupContainer( "grandSelectAllContainer" );
            grandSelectAllContainer.setVisible( false );
            grandSelectAllContainer.setOutputMarkupId( true );
            grandSelectAllContainer.setOutputMarkupPlaceholderTag( true );

            add( grandSelectAllContainer );

            AjaxSubmitLink grandSelectAllLink = new AjaxSubmitLink( "grandSelectAllLink" )
            {
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    handleGrandSelectAll( target, true, true, false );
                }
            };

            grandSelectAllContainer.add( grandSelectAllLink );

            totalSelectedLabel = new Label( "totalSelectedLabel", new Model( "0" ) );
            grandSelectAllContainer.add( totalSelectedLabel );

            Label totalItem1Label = new Label( "totalItem1Label", new Model( totalItems ) );
            grandSelectAllLink.add( totalItem1Label );
        }

        private void initGrandSelectNoneComponents()
        {
            grandSelectNoneContainer = new WebMarkupContainer( "grandSelectNoneContainer" );
            grandSelectNoneContainer.setVisible( false );
            grandSelectNoneContainer.setOutputMarkupId( true );
            grandSelectNoneContainer.setOutputMarkupPlaceholderTag( true );

            add( grandSelectNoneContainer );

            AjaxSubmitLink grandSelectNoneLink = new AjaxSubmitLink( "grandSelectNoneLink" )
            {
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    handleGrandSelectAll( target, false, false, false );
                    handleSelectAll( target, false );
                }
            };

            grandSelectNoneContainer.add( grandSelectNoneLink );

            Label totalItem2Label = new Label( "totalItem2Label", new Model( totalItems ) );
            grandSelectNoneContainer.add( totalItem2Label );
        }

        protected void delegateSubmit( IFormSubmittingComponent submittingButton )
        {
            actionBar.onSubmitting( submittingButton );
        }
    }

    private void updateCurrBatchAndGrandSelectVisibility()
    {
        isGrandAllSelected = false;

        grandSelectAllOrNoneVisible( false, false );
    }

    private void handleItemCheckBoxChanged( CheckBox checkBox, AjaxRequestTarget target )
    {
        if( !Boolean.parseBoolean( checkBox.getModelObjectAsString() ) )
        {
            handleGrandSelectAll( target, false, false, false );
        }
    }

    private void handleGrandSelectAll( AjaxRequestTarget target, boolean isSelectAll,
                                       boolean selectNoneVisible, boolean selectAllVisible )
    {
        grandSelectAllOrNoneVisible( selectNoneVisible, selectAllVisible );

        isGrandAllSelected = isSelectAll;

        target.addComponent( grandSelectAllContainer );
        target.addComponent( grandSelectNoneContainer );
    }

    private void grandSelectAllOrNoneVisible( boolean selectNoneVisible, boolean selectAllVisible )
    {
        grandSelectNoneContainer.setVisible( selectNoneVisible );
        grandSelectAllContainer.setVisible( selectAllVisible );
    }

    private void handleSelectAll( AjaxRequestTarget target, boolean isSelectAll )
    {
        selectedIds.clear();

        for( int i = 0; i < currBatchIds.size(); i++ )
        {
            if( isSelectAll )
            {
                selectedIds.add( currBatchIds.get( i ) );
            }

            CheckBox checkBox = currBatchCheckBoxs.get( i );

            checkBox.setModel( new Model( isSelectAll ) );

            target.addComponent( checkBox );
        }

        handleGrandSelectAll( target, false, false, false );

        if( isSelectAll )
        {
            if( totalItems > dataView.getItemsPerPage() )
            {
                totalSelectedLabel.setModelObject( selectedIds.size() );

                grandSelectAllContainer.setVisible( true );

                target.addComponent( grandSelectAllContainer );
            }
        }
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
        if( !isGrandAllSelected )
        {
            return new SubSetSortableDataProvider<T>( selectedIds )
            {
                public String getId( T o )
                {
                    return getDetachableDataProvider().getId( o );
                }

                public T load( String id )
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
