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
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.ui.common.NewLinkPanel;
import org.qi4j.chronos.ui.common.tab.NewLinkTab;

public abstract class ContactPersonTab extends NewLinkTab
{
    public ContactPersonTab()
    {
        super( "Contact Persons" );
    }

    public NewLinkPanel getNewLinkPanel( String panelId )
    {
        return new ContactPersonNewLinkPanel( panelId );
    }

    private class ContactPersonNewLinkPanel extends NewLinkPanel
    {
        public ContactPersonNewLinkPanel( String id )
        {
            super( id );
        }

        public Panel getContent( String id )
        {
            return new ContactPersonTable( id )
            {
                public HasContactPersons getHasContactPersons()
                {
                    return ContactPersonTab.this.getCustomer();
                }

                public Customer getCustomer()
                {
                    return ContactPersonTab.this.getCustomer();
                }
            };
        }

        public void newLinkOnClick()
        {
/*
            setResponsePage(
                new ContactPersonAddPage( this.getPage() )
                {
                    public Customer getCustomer()
                    {
                        return ContactPersonTab.this.getCustomer();
                    }
                }
            );
*/
        }

        public String getNewLinkText()
        {
            return "New Contact Person";
        }
    }

    public abstract Customer getCustomer();
}
