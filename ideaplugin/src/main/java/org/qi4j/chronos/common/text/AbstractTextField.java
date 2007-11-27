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
package org.qi4j.chronos.common.text;

import javax.swing.JTextField;
import javax.swing.text.Document;
import org.qi4j.chronos.util.UiUtil;

public abstract class AbstractTextField extends JTextField
{
    public AbstractTextField()
    {
        super();

        initListeners();
    }

    public AbstractTextField( String text )
    {
        super( text );

        initListeners();
    }

    public AbstractTextField( Document doc, String text, int columns )
    {
        super( doc, text, columns );

        initListeners();
    }

    private void initListeners()
    {
        UiUtil.addSelectAllTextOnFocus( this );
    }
}

