/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.ui.account.lab;

import org.qi4j.chronos.model.Reference;
import java.util.Comparator;

public class DescendingReferenceComparator implements Comparator<Reference>
{
    public int compare( Reference o1, Reference o2 )
    {
        return o2.reference().get().compareTo( o1.reference().get() );
    }
}