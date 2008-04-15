/*
 * Copyright (c) 2008, kamil. All Rights Reserved.
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
package org.qi4j.chronos.test.util;

import java.util.Map;
import java.util.HashMap;
import org.qi4j.chronos.model.composites.ProjectRoleComposite;
import org.qi4j.composite.CompositeBuilder;

public class CompImpl implements Comp<ProjectRoleComposite>
{
    private final Map<String, SomeObjects> m_map;

    public static final String NAME = "HWEUI283535J34";

    public static final String NAME_U = "IUWEFB892532KL";

    public static final String ID = "JHFWE89732532LSFD";

    public static final String ID_U = "KLJFPWE73232HL32";

    public CompImpl( CompositeBuilder<? extends ProjectRoleComposite> compositeBuilder )
    {
        ProjectRoleComposite projectRoleComposite = compositeBuilder.newInstance();
        m_map = new HashMap<String, SomeObjects>();
        m_map.put( projectRoleComposite.name().name(), new SomeObjectsImpl( NAME, NAME_U ) );
        m_map.put( projectRoleComposite.identity().name(), new SomeObjectsImpl( ID, ID_U ) );
    }

    public Map<String, SomeObjects> getValue()
    {
        return m_map;
    }
}
