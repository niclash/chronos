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
package org.qi4j.chronos.model.mixins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.associations.HasTaskMasters;
import org.qi4j.chronos.model.composites.TaskEntityComposite;

public class HasTaskMastersMixin implements HasTaskMasters
{
    private List<TaskEntityComposite> list;

    public HasTaskMastersMixin()
    {
        list = new ArrayList<TaskEntityComposite>();
    }

    public void addTaskMaster( TaskEntityComposite taskMaster )
    {
        list.add( taskMaster );
    }

    public void removeTaskMaster( TaskEntityComposite taskMaster )
    {
        list.remove( taskMaster );
    }

    public Iterator<TaskEntityComposite> taskMasterIteraotr()
    {
        return list.iterator();
    }
}
