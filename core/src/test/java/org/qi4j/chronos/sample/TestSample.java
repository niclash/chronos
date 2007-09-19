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
package org.qi4j.chronos.sample;

import org.qi4j.api.CompositeBuilder;
import org.qi4j.api.CompositeBuilderFactory;
import static org.qi4j.api.PropertyValue.name;
import static org.qi4j.api.PropertyValue.property;
import org.qi4j.runtime.CompositeBuilderFactoryImpl;

public class TestSample
{
    public static void main( String[] args )
    {
        CompositeBuilderFactory factory = new CompositeBuilderFactoryImpl();

        CompositeBuilder<SampleComposite> sampleBuilder = factory.newCompositeBuilder( SampleComposite.class );

        sampleBuilder.properties( Sample.class, property( name( Sample.class ).getSampleName(), "ABC" ) );

        Sample sample = sampleBuilder.newInstance();

        System.err.println( "sampleName " + sample.getSampleName() );
    }
}
