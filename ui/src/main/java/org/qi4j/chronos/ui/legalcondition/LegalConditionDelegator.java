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
package org.qi4j.chronos.ui.legalcondition;

import java.io.Serializable;
import org.qi4j.chronos.model.LegalCondition;

//TODO bp. Don't need this when we have ValueObjectComposite
public class LegalConditionDelegator implements Serializable
{
    private String name;
    private String desc;

    public LegalConditionDelegator( LegalCondition legalCondition )
    {
        name = legalCondition.name().get();
        desc = legalCondition.description().get();
    }

    public String getName()
    {
        return name;
    }

    public String getDesc()
    {
        return desc;
    }

    public boolean equals( Object o )
    {
        if( this == o )
        {
            return true;
        }
        if( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        LegalConditionDelegator that = (LegalConditionDelegator) o;

        if( desc != null ? !desc.equals( that.desc ) : that.desc != null )
        {
            return false;
        }
        if( name != null ? !name.equals( that.name ) : that.name != null )
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        int result;
        result = ( name != null ? name.hashCode() : 0 );
        result = 31 * result + ( desc != null ? desc.hashCode() : 0 );
        return result;
    }

    public String toString()
    {
        return name;
    }
}
