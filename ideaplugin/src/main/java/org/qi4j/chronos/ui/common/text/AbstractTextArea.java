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
package org.qi4j.chronos.ui.common.text;

import javax.swing.JTextArea;
import javax.swing.text.Document;
import org.qi4j.chronos.ui.util.SwingMiscUtil;

public abstract class AbstractTextArea extends JTextArea
{
    public AbstractTextArea()
    {
        super();

        initListeners();
    }

    public AbstractTextArea( String text )
    {
        super( text );

        initListeners();
    }

    public AbstractTextArea( int rows, int columns )
    {
        super( rows, columns );

        initListeners();
    }

    public AbstractTextArea( String text, int rows, int columns )
    {
        super( text, rows, columns );

        initListeners();
    }

    public AbstractTextArea( Document doc )
    {
        super( doc );

        initListeners();
    }

    public AbstractTextArea( Document doc, String text, int rows, int columns )
    {
        super( doc, text, rows, columns );

        initListeners();
    }

    private void initListeners()
    {
        SwingMiscUtil.addSelectAllTextOnFocus( this );
    }
}
