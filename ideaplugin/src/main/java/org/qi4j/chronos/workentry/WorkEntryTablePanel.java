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
package org.qi4j.chronos.workentry;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPopupMenu;
import com.intellij.openapi.project.Project;
import com.intellij.ui.PopupHandler;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.Component;
import java.awt.Container;
import java.util.List;
import javax.swing.ListSelectionModel;
import org.qi4j.chronos.action.ChronosActionConstant;
import org.qi4j.chronos.common.AbstractPanel;
import org.qi4j.chronos.common.ChronosDataProvider;
import org.qi4j.chronos.common.ChronosPageableWrapper;
import org.qi4j.chronos.common.ChronosTable;
import org.qi4j.chronos.common.ChronosTableModel;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.WorkEntryService;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.chronos.util.UiUtil;

public class WorkEntryTablePanel extends AbstractPanel
{
    private final static String[] COL_NAMES = { "Started Date", "End Date", "Duration", "By", "Title" };
    private final static int[] COL_WITDHS = { 150, 150, 100, 80, 300 };

    private ChronosPageableWrapper<WorkEntry> pageableWrapper;

    private Project project;

    public WorkEntryTablePanel( Project project )
    {
        this.project = project;

        init();
    }

    public void resetData()
    {
        pageableWrapper.resetData();
    }

    protected String getLayoutColSpec()
    {
        return "1dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "1dlu:grow";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( pageableWrapper, cc.xy( 1, 1, "fill, fill" ) );
    }

    protected void initComponents()
    {
        pageableWrapper = new ChronosPageableWrapper<WorkEntry>( new WorkEntryDataProvider(), COL_NAMES, COL_WITDHS )
        {
            protected ChronosTable createTable( String[] colNames, int[] colWidths )
            {
                return WorkEntryTablePanel.this.createTable( colNames, colWidths );
            }
        };
    }

    private ChronosTable createTable( String[] colNames, int[] colWidths )
    {
        Table table = new Table( new ChronosTableModel( colNames ) );

        UiUtil.initTableWidth( table, colWidths );

        table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        return table;
    }


    private ActionManager getActionManager()
    {
        return ActionManager.getInstance();
    }

    private class Table extends ChronosTable
        implements WorkEntryListComponent
    {
        public Table( ChronosTableModel model )
        {
            super( model );

            initListeners();
        }

        private void initListeners()
        {
            PopupHandler popupHandler = new PopupHandler()
            {
                public void invokePopup( Component comp, int x, int y )
                {
                    ActionManager actionManager = getActionManager();

                    ActionGroup group = (ActionGroup) actionManager.getAction( ChronosActionConstant.WORKENTRY_LIST_GROUP );
                    ActionPopupMenu popupMenu = actionManager.createActionPopupMenu( "POPUP", group );

                    if( popupMenu != null )
                    {
                        popupMenu.getComponent().show( comp, x, y );
                    }
                }
            };

            this.addMouseListener( popupHandler );
        }

        public WorkEntry getSelectedWorkEntry()
        {
            return pageableWrapper.getSelectedItem();
        }

        public WorkEntry[] getSelectedWorkEntries()
        {
            return new WorkEntry[]{ getSelectedWorkEntry() };
        }

        public void refreshList()
        {
            pageableWrapper.resetData();
        }

        public Container getComponent()
        {
            return this;
        }
    }

    private WorkEntryService getWorkEntryService()
    {
        return getServices( project ).getWorkEntryService();
    }

    private class WorkEntryDataProvider implements ChronosDataProvider<WorkEntry>
    {
        public List<WorkEntry> getData( int first, int count )
        {
            return getWorkEntryService().findAll( getChronosProject( project ),
                                                  getStaff( project ), new FindFilter( first, count ) );
        }

        public Object[] populateData( WorkEntry workEntry )
        {
            return new Object[]{
                DateUtil.formatDateTime( workEntry.startTime().get() ),
                DateUtil.formatDateTime( workEntry.endTime().get() ),
                DateUtil.getTimeDifferent( workEntry.startTime().get(), workEntry.endTime().get() ),
                workEntry.projectAssignee().get().staff().get().fullName().get(),
                workEntry.title().get()
            };
        }

        public int getSize()
        {
            return getWorkEntryService().countAll( getChronosProject( project ), getStaff( project ) );
        }
    }
}
