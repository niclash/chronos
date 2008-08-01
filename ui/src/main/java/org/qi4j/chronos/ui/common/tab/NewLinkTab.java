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
package org.qi4j.chronos.ui.common.tab;

import org.qi4j.chronos.ui.common.BorderPanel;
import org.qi4j.chronos.ui.common.NewLinkPanel;

public abstract class NewLinkTab extends BaseTab
{
    private static final long serialVersionUID = 1L;

    public NewLinkTab( String title )
    {
        super( title );
    }

    public final BorderPanel getBorderPanel( final String id )
    {
        return getNewLinkPanel( id );
    }

    public abstract NewLinkPanel getNewLinkPanel( String id );
}
