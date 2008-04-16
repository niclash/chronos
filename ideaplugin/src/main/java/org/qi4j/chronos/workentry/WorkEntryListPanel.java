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

import com.intellij.openapi.project.Project;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ListSelectionModel;
import org.qi4j.chronos.common.AbstractPanel;
import org.qi4j.chronos.common.ChronosTable;
import org.qi4j.chronos.common.ChronosTableModel;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.chronos.util.UiUtil;

public class WorkEntryListPanel extends AbstractPanel
{
    private final static String[] COL_NAMES = { "Started Date", "End Date", "Duration", "Added By", "Title" };
    private final static int[] COL_WITDHS = { 150, 150, 100, 80, 300 };

    private ChronosTable workEntryTable;

    private List<WorkEntry> workEntries;
    private final Project project;

    public WorkEntryListPanel( Project project )
    {
        this.project = project;
        init();
    }

    public void initData( List<WorkEntry> workEntries )
    {
        this.workEntries = workEntries;

        for( WorkEntry workEntry : workEntries )
        {
            insertWorkEntry( workEntry );
        }
    }

    private void insertWorkEntry( WorkEntry workEntry )
    {
        workEntryTable.insertToLastRow(
            DateUtil.formatDateTime( workEntry.startTime().get() ),
            DateUtil.formatDateTime( workEntry.endTime().get() ),
            DateUtil.getTimeDifferent( workEntry.startTime().get(), workEntry.endTime().get() ),
            workEntry.projectAssignee().get().staff().get().fullName().get(),
            workEntry.title().get() );
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
        builder.add( UiUtil.createScrollPanel( workEntryTable ), cc.xy( 1, 1, "fill, fill" ) );
    }

    protected void initComponents()
    {
        workEntryTable = UiUtil.createTable( new ChronosTableModel( COL_NAMES ), COL_WITDHS );

        workEntryTable.getSelectionModel().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        initListeners();
    }

    private void initListeners()
    {
        workEntryTable.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent e )
            {
                //only interested left double click
                if( e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 )
                {
                    handleRowSelected();
                }
            }
        } );
    }

    private void handleRowSelected()
    {
        int row = workEntryTable.getSelectedRow();

        if( row != -1 )
        {
            final WorkEntry workEntry = workEntries.get( row );

            WorkEntryDetailDialog detailDialog = new WorkEntryDetailDialog( project )
            {
                public WorkEntry getWorkEntry()
                {
                    return workEntry;
                }
            };

            detailDialog.show();
        }
    }
}
