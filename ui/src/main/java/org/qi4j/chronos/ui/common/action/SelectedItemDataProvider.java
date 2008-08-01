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
package org.qi4j.chronos.ui.common.action;

import java.util.ArrayList;
import java.util.List;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.entity.Identity;

abstract class SelectedItemDataProvider<T extends Identity> extends AbstractSortableDataProvider<T>
{
    private static final long serialVersionUID = 1L;

    private List<String> selectedIdList;

    SelectedItemDataProvider( List<String> selectedIdList )
    {
        this.selectedIdList = selectedIdList;
    }

    public final List<T> dataList( int first, int count )
    {
        int toIndex = first + count;

        //tune the buffer to avoid IndexOutOfBoundsException
        if( toIndex > selectedIdList.size() )
        {
            toIndex = selectedIdList.size();
        }

        List<String> idList = this.selectedIdList.subList( first, toIndex );

        List<T> resultList = new ArrayList<T>();

        for( String id : idList )
        {
            T t = load( id ).getObject();

            resultList.add( t );
        }

        return resultList;
    }

    public int size()
    {
        return selectedIdList.size();
    }
}
