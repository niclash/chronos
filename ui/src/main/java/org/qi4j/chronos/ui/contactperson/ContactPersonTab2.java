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
package org.qi4j.chronos.ui.contactperson;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.ui.common.BorderPanel;
import org.qi4j.chronos.ui.common.BorderPanelWrapper;
import org.qi4j.chronos.ui.common.tab.BaseTab;

public final class ContactPersonTab2 extends BaseTab
{
    private static final long serialVersionUID = 1L;

    private IModel<HasContactPersons> hasContactPersons;

    public ContactPersonTab2( String title, IModel<HasContactPersons> hasContactPersons )
    {
        super( title );

        this.hasContactPersons = hasContactPersons;
    }

    public BorderPanel getBorderPanel( String panelId )
    {
        return new BorderPanelWrapper( panelId )
        {
            private static final long serialVersionUID = 1L;

            public Panel getWrappedPanel( String panelId )
            {
                ContactPersonTable table = new ContactPersonTable( panelId, hasContactPersons, new ContactPersonDataProvider( hasContactPersons ) );

                table.setNavigatorVisible( false );
                table.setActionBarVisible( false );

                return table;
            }
        };
    }

}
