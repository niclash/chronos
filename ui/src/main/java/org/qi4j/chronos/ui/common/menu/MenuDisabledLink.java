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
package org.qi4j.chronos.ui.common.menu;

import org.apache.wicket.markup.html.basic.Label;

public class MenuDisabledLink extends MenuItem
{
    private static final long serialVersionUID = 1L;

    private Label label;

    public MenuDisabledLink( String text )
    {
        super();

        label = new Label( "linkLabel", text );

        add( label );
    }
}