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
package org.qi4j.chronos.ui.common;

import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;

public class SimpleDropDownChoice extends DropDownChoice
{
    private String choice;

    private boolean setDefaultValue;

    public SimpleDropDownChoice( String id, List<String> optionList, boolean setDefaultValue )
    {
        super( id );

        this.setDefaultValue = setDefaultValue;
        this.setModel( new PropertyModel( this, "choice" ) );

        setNewChoices( optionList );
    }

    private void update( List<String> optionList )
    {
        choice = null;

        boolean hasData = optionList.size() != 0;

        if( hasData && setDefaultValue )
        {
            choice = optionList.get( 0 );
        }

        this.setEnabled( hasData );
    }

    public String getChoice()
    {
        return choice;
    }

    public void setChoice( String choice )
    {
        this.choice = choice;
    }

    public void setNewChoices( List<String> list )
    {
        super.setChoices( list );

        update( list );
    }
}

