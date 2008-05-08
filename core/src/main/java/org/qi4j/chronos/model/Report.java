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
package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasProject;
import org.qi4j.chronos.model.associations.HasReportSummary;
import org.qi4j.chronos.model.composites.ReportEntityComposite;
import org.qi4j.composite.Mixins;
import org.qi4j.composite.scope.This;
import org.qi4j.property.ImmutableProperty;
import org.qi4j.property.ComputedPropertyInstance;
import org.qi4j.property.PropertyInfo;
import org.qi4j.property.GenericPropertyInfo;
import java.text.DateFormat;

@Mixins( Report.DescriptorMixin.class )
public interface Report extends Descriptor, Name, TimeRange, HasProject, HasReportSummary
{
    static final class DescriptorMixin implements Descriptor
    {
        private static PropertyInfo DISPLAY_VALUE;

        static
        {
            try
            {
                DISPLAY_VALUE =
                    new GenericPropertyInfo( ReportEntityComposite.class.getMethod( "displayValue" ) );
            }
            catch( NoSuchMethodException nsme )
            {
                java.lang.System.err.println( "Error: " + nsme.getLocalizedMessage() );
                nsme.printStackTrace();
            }
        }

        private @This Report report;

        public ImmutableProperty<String> displayValue()
        {
            return new ComputedPropertyInstance<String>( DISPLAY_VALUE )
            {
                private String m_displayValue = "Project Report: " + report.project().get().name().get() + " from " +
                      DateFormat.getDateInstance().format( report.startTime().get() ) + " to " +
                      DateFormat.getDateInstance().format( report.endTime().get() );

                public String get()
                {
                    return m_displayValue;
                }
            };
        }
    }
}
