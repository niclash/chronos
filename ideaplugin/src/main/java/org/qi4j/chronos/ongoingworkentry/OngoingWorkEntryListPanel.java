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
package org.qi4j.chronos.ongoingworkentry;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.util.List;
import org.qi4j.chronos.common.AbstractPanel;
import org.qi4j.chronos.common.ChronosTable;
import org.qi4j.chronos.common.ChronosTableModel;
import org.qi4j.chronos.model.composites.OngoingWorkEntryEntityComposite;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.chronos.util.UiUtil;

public class OngoingWorkEntryListPanel extends AbstractPanel
{
    private final static String[] COL_NAMES = { "Started Date", "By" };
    private final static int[] COL_WITDHS = { 150, 200 };

    private ChronosTable table;

    public OngoingWorkEntryListPanel()
    {
        init();
    }

    public void initData( List<OngoingWorkEntryEntityComposite> ongoingWorkEntries )
    {
        for( OngoingWorkEntryEntityComposite ongoingWorkEntry : ongoingWorkEntries )
        {
            insertOngoingWorkEntry( ongoingWorkEntry );
        }
    }

    private void insertOngoingWorkEntry( OngoingWorkEntryEntityComposite workEntry )
    {
        table.insertToLastRow(
            DateUtil.formatDateTime( workEntry.createdDate().get() ),
            workEntry.projectAssignee().get().staff().get().getFullname() );
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
        builder.add( UiUtil.createScrollPanel( table ), cc.xy( 1, 1, "fill,fill" ) );
    }

    protected void initComponents()
    {
        table = UiUtil.createTable( new ChronosTableModel( COL_NAMES ), COL_WITDHS );
    }
}
