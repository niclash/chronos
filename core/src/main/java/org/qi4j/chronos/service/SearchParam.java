/*
 * Copyright 2007 Lan Boon Ping. All Rights Reserved.
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
package org.qi4j.chronos.service;

public final class SearchParam
{
    private int first;
    private int count;

    private String sortField;
    private boolean sortAscending;

    public SearchParam( int first, int count )
    {
        this( first, count, null, true );
    }

    public SearchParam( int first, int count, String sortField, boolean sortAscending )
    {
        this.first = first;
        this.count = count;
        this.sortField = sortField;
        this.sortAscending = sortAscending;
    }

    public int getCount()
    {
        return count;
    }

    public int getFirst()
    {
        return first;
    }

    public String getSortField()
    {
        return sortField;
    }

    public boolean isSortAscending()
    {
        return sortAscending;
    }

    public boolean hasSort()
    {
        return sortField != null;
    }
}

