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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.api.util.NullArgumentException;
import org.qi4j.api.entity.Identity;

public abstract class ActionTable<T extends Identity> extends Panel
{
    private static final long serialVersionUID = 1L;

    private static final String WICKET_ID_ACTION_TABLE_FORM = "actionTableForm";

    private final static String CHECKBOX_CLASS_NAME = "checkBoxClass";

    public final static int DEFAULT_ITEM_PER_PAGE = 10;

    private ActionBar actionBar;

    private DataView<T> dataView;

    private Set<String> selectedIds;

    private List<String> currIds;

    private List<CheckBox> currCheckBoxs;

    private Label totalSelectedLabel;

    private WebMarkupContainer grandSelectAllContainer;

    private WebMarkupContainer grandSelectNoneContainer;

    private WebMarkupContainer navigatorGroupContainer;

    private WebMarkupContainer selectAllOrNoneContainer;

    private WebMarkupContainer checkBoxHeaderContainer;

    private boolean isGrandAllSelected = false;

    private AbstractSortableDataProvider<T> dataProvider;

    private String[] headerNames;

    public ActionTable( String id, IModel model, AbstractSortableDataProvider<T> dataProvider, String[] headerNames )
    {
        super( id, model );

        NullArgumentException.validateNotNull( "dataProvider", dataProvider );
        NullArgumentException.validateNotNull( "headerNames", headerNames );

        this.dataProvider = dataProvider;
        this.headerNames = headerNames;

        actionBar = new ActionBar( this );

        selectedIds = new HashSet<String>();
        currIds = new ArrayList<String>();
        currCheckBoxs = new ArrayList<CheckBox>();

        ActionTableForm form = new ActionTableForm( WICKET_ID_ACTION_TABLE_FORM );
        add( form );
        authorizingActionBar();
    }

    private void authorizingActionBar()
    {
        authorizingActionBar( actionBar );
        authorizingActionBar( selectAllOrNoneContainer );

        updateCheckBoxHeaderContainerVisibility();
    }

    protected void authorizingActionBar( Component component )
    {
        //override this
    }

    public void setItemPerPage( int itemPerPage )
    {
        dataView.setItemsPerPage( itemPerPage );
    }

    public void setActionBarVisible( boolean visible )
    {
        actionBar.setVisible( visible );
        selectAllOrNoneContainer.setVisible( visible );

        updateCheckBoxHeaderContainerVisibility();
    }

    public void setNavigatorVisible( boolean isVisible )
    {
        navigatorGroupContainer.setVisible( isVisible );

        updateCheckBoxHeaderContainerVisibility();
    }

    private void updateCheckBoxHeaderContainerVisibility()
    {
        checkBoxHeaderContainer.setVisible( isItemCheckBoxVisible() );
    }

    private boolean isItemCheckBoxVisible()
    {
        return actionBar.isVisible() && navigatorGroupContainer.isVisible() && selectAllOrNoneContainer.isVisible();
    }

    private class ActionTableForm extends Form
    {
        private static final long serialVersionUID = 1L;

        public ActionTableForm( String aWicketId )
        {
            super( aWicketId );

            add( actionBar );

            initDataViewComponent();
            initNavigatorGroupComponent();

            checkBoxHeaderContainer = new WebMarkupContainer( "checkBoxHeaderContainer" );
            add( checkBoxHeaderContainer );
        }

        @SuppressWarnings( "unchecked" )
        private void initDataViewComponent()
        {
            Loop headers = new Loop( "headers", headerNames.length )
            {
                private static final long serialVersionUID = 1L;

                protected void populateItem( LoopItem item )
                {
                    final int index = item.getIteration();

                    String header = headerNames[ index ];

                    item.add( new Label( "header", header ) );
                }
            };

            add( headers );

            dataView = new DataView<T>( "dataView", dataProvider )
            {
                private static final long serialVersionUID = 1L;

                @SuppressWarnings( { "unchecked" } )
                @Override
                protected void populateItem( Item item )
                {
                    if( item.getIndex() == 0 )
                    {
                        currIds.clear();
                        currCheckBoxs.clear();
                    }

                    T obj = (T) item.getModelObject();

                    final String id = obj.identity().get();

                    final CheckBox checkBox = new CheckBox( "itemCheckBox", new CheckedModel( id ) );
                    checkBox.add( new AttributeModifier( "class", true, new Model<String>( CHECKBOX_CLASS_NAME ) ) );

                    checkBox.add( new AjaxFormComponentUpdatingBehavior( "onchange" )
                    {
                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void onUpdate( AjaxRequestTarget target )
                        {
                            handleItemCheckBoxChanged( id, checkBox, target );
                        }
                    } );

                    WebMarkupContainer checkBoxContainer = new WebMarkupContainer( "checkBoxContainer" );

                    checkBoxContainer.add( checkBox );

                    checkBoxContainer.setVisible( isItemCheckBoxVisible() );

                    item.add( checkBoxContainer );

                    ActionTable.this.populateItems( item );

                    if( item.getIndex() % 2 == 0 )
                    {
                        item.add( new AttributeModifier( "class", true, new Model<String>( "alt" ) ) );
                    }

                    currIds.add( id );
                    currCheckBoxs.add( checkBox );
                }
            };

            add( dataView );

            dataView.setItemsPerPage( DEFAULT_ITEM_PER_PAGE );
        }

        private void initNavigatorGroupComponent()
        {
            navigatorGroupContainer = new WebMarkupContainer( "navigatorGroupContainer" );

            AjaxSubmitLink selectAllLink = new AjaxSubmitLink( "selectAllLink" )
            {
                private static final long serialVersionUID = 1L;

                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    handleSelectAll( target, true );
                }
            };

            AjaxSubmitLink selectNoneLink = new AjaxSubmitLink( "selectNoneLink" )
            {
                private static final long serialVersionUID = 1L;

                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    handleSelectAll( target, false );
                }
            };

            ActionTableNavigatorBar navigatorBar = new ActionTableNavigatorBar( "navigator", dataView )
            {
                private static final long serialVersionUID = 1L;

                public void beforeItemPerPageChanged()
                {
                    selectedIds.clear();

                    updateCurrBatchAndGrandSelectVisibility();

                    enableOrDisableActionBar( null );
                }

                public void beforeNextNagivation()
                {
                    updateCurrBatchAndGrandSelectVisibility();
                }
            };

            selectAllOrNoneContainer = new WebMarkupContainer( "selectAllOrNoneContainer" );

            selectAllOrNoneContainer.add( selectAllLink );
            selectAllOrNoneContainer.add( selectNoneLink );

            navigatorGroupContainer.add( selectAllOrNoneContainer );
            navigatorGroupContainer.add( navigatorBar );

            add( navigatorGroupContainer );

            initGrandSelectAllComponents();
            initGrandSelectNoneComponents();
        }

        private void initGrandSelectAllComponents()
        {
            grandSelectAllContainer = new WebMarkupContainer( "grandSelectAllContainer" );
            grandSelectAllContainer.setVisible( false );
            grandSelectAllContainer.setOutputMarkupId( true );
            grandSelectAllContainer.setOutputMarkupPlaceholderTag( true );

            navigatorGroupContainer.add( grandSelectAllContainer );

            AjaxSubmitLink grandSelectAllLink = new AjaxSubmitLink( "grandSelectAllLink" )
            {
                private static final long serialVersionUID = 1L;

                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    handleGrandSelectAll( target, true, true, false );
                }
            };

            grandSelectAllContainer.add( grandSelectAllLink );

            totalSelectedLabel = new Label( "totalSelectedLabel", new Model<String>( "0" ) );
            grandSelectAllContainer.add( totalSelectedLabel );

            Label totalItem1Label = new Label( "totalItem1Label", new PropertyModel( dataProvider, "size" ) );
            grandSelectAllLink.add( totalItem1Label );
        }

        private void initGrandSelectNoneComponents()
        {
            grandSelectNoneContainer = new WebMarkupContainer( "grandSelectNoneContainer" );
            grandSelectNoneContainer.setVisible( false );
            grandSelectNoneContainer.setOutputMarkupId( true );
            grandSelectNoneContainer.setOutputMarkupPlaceholderTag( true );

            navigatorGroupContainer.add( grandSelectNoneContainer );

            AjaxSubmitLink grandSelectNoneLink = new AjaxSubmitLink( "grandSelectNoneLink" )
            {
                private static final long serialVersionUID = 1L;

                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    handleGrandSelectAll( target, false, false, false );
                    handleSelectAll( target, false );
                }
            };

            grandSelectNoneContainer.add( grandSelectNoneLink );

            Label totalItem2Label = new Label( "totalItem2Label", new PropertyModel( dataProvider, "size" ) );
            grandSelectNoneContainer.add( totalItem2Label );
        }
    }

    private void updateCurrBatchAndGrandSelectVisibility()
    {
        isGrandAllSelected = false;

        grandSelectAllOrNoneVisible( false, false );
    }

    private void handleItemCheckBoxChanged( String id, CheckBox checkBox, AjaxRequestTarget target )
    {
        if( !Boolean.parseBoolean( checkBox.getDefaultModelObjectAsString() ) )
        {
            handleGrandSelectAll( target, false, false, false );

            selectedIds.remove( id );
        }
        else
        {
            selectedIds.add( id );
        }

        enableOrDisableActionBar( target );
    }

    private void enableOrDisableActionBar( AjaxRequestTarget target )
    {
        actionBar.setActionBarEnabled( selectedIds.size() != 0, target );
    }

    private void handleGrandSelectAll( AjaxRequestTarget target, boolean isSelectAll,
                                       boolean selectNoneVisible, boolean selectAllVisible )
    {
        if( isGrandAllSelected && isSelectAll )
        {
            return;
        }

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

        for( int i = 0; i < currIds.size(); i++ )
        {
            if( isSelectAll )
            {
                selectedIds.add( currIds.get( i ) );
            }

            CheckBox checkBox = currCheckBoxs.get( i );

            checkBox.setModel( new Model<Boolean>( isSelectAll ) );

            target.addComponent( checkBox );
        }

        handleGrandSelectAll( target, false, false, false );

        if( isSelectAll )
        {
            IDataProvider dataProvider = dataView.getDataProvider();
            int totalItems = dataProvider.size();
            if( totalItems > dataView.getItemsPerPage() )
            {
                totalSelectedLabel.setDefaultModelObject( selectedIds.size() );

                grandSelectAllContainer.setVisible( true );

                target.addComponent( grandSelectAllContainer );
            }
        }

        enableOrDisableActionBar( target );
    }

    void resetCurrBatchData()
    {
        selectedIds.clear();
        currIds.clear();
        currCheckBoxs.clear();
    }

    public void dataViewModelChanged()
    {
        dataView.modelChanged();
    }

    public final void addAction( Action action )
    {
        actionBar.addAction( action );
    }

    public final void removeAction( Action action )
    {
        actionBar.removeAction( action );
    }

    final boolean isSelectedItems()
    {
        if( dataProvider.size() == 0 )
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
            return new SelectedItemDataProvider<T>( new ArrayList<String>( selectedIds ) )
            {
                private static final long serialVersionUID = 1L;

                public IModel<T> load( String id )
                {
                    return dataProvider.load( id );
                }
            };
        }
        else
        {
            return dataProvider;
        }
    }

    private class CheckedModel extends AbstractCheckBoxModel
    {
        private static final long serialVersionUID = 1L;

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

    public abstract void populateItems( Item<T> item );
}
