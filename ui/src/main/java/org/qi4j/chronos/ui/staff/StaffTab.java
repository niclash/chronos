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
package org.qi4j.chronos.ui.staff;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.associations.HasStaffs;
import org.qi4j.chronos.ui.common.BorderPanel;
import org.qi4j.chronos.ui.common.BorderPanelWrapper;
import org.qi4j.chronos.ui.common.tab.BaseTab;

public final class StaffTab extends BaseTab
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasStaffs> hasStaffs;

    public StaffTab( String title, IModel<? extends HasStaffs> hasStaffs )
    {
        super( title );

        this.hasStaffs = hasStaffs;
    }

    public BorderPanel getBorderPanel( String panelId )
    {
        return new BorderPanelWrapper( panelId )
        {
            private static final long serialVersionUID = 1L;

            public Panel getWrappedPanel( String panelId )
            {
                return new StaffTable( panelId, hasStaffs, new StaffDataProvider( hasStaffs ) );
            }
        };
    }
}
