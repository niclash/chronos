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
package org.qi4j.ui;

import java.util.HashMap;
import java.util.Map;
import org.qi4j.ui.component.composites.PageComposite;

public class HasBookmarkablePageMixin implements HasBookmarkablePage
{
    private Map<String, Class<? extends PageComposite>> map;

    public HasBookmarkablePageMixin()
    {
        map = new HashMap<String, Class<? extends PageComposite>>();
    }

    public void addPage( String path, Class<? extends PageComposite> page )
    {
        map.put( path, page );
    }

    public void removePage( String path )
    {
        map.remove( path );
    }

    public Class<? extends PageComposite> getPage( String path )
    {
        return map.get( path );
    }
}
