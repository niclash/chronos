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
package org.qi4j.chronos.util;

import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.EntityComposite;
import org.qi4j.query.QueryBuilderFactory;
import org.qi4j.query.QueryBuilder;
import org.qi4j.query.Query;
import org.qi4j.query.grammar.BooleanExpression;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class UnitOfWorkUtil
{
    public static final UnitOfWork getTestUnitOfWork( final UnitOfWork uow, final Map<Class<? extends EntityComposite>, List<? extends EntityComposite>> map )
    {
        // Only added to get around the as yet not implemented Query interface
        InvocationHandler handler = new InvocationHandler()
        {

            public Object invoke( final Object proxy, Method method, Object[] args ) throws Throwable
            {
                if( method.getName().equals( "queryBuilderFactory" ))
                {
                    return new QueryBuilderFactory() {

                        public <T> QueryBuilder<T> newQueryBuilder( final Class<T> compositeType )
                        {
                            return new QueryBuilder<T>()
                            {

                                public QueryBuilder<T> where( BooleanExpression expressions )
                                {
                                    throw new UnsupportedOperationException( "Not implmented" );
                                }

                                public Query<T> newQuery()
                                {
                                    InvocationHandler qHandler = new InvocationHandler()
                                    {
                                        public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
                                        {
                                            if(method.getName().equals( "iterator" ))
                                            {
                                                List<? extends EntityComposite> list = map.get(compositeType);
                                                if(list == null)
                                                {
                                                    list = new ArrayList<EntityComposite>();
                                                }
                                                return list.iterator();
                                            }
                                            else if(method.getDeclaringClass().equals( Object.class ))
                                            {
                                                return method.invoke( this, args );
                                            }
                                            else
                                            {
                                                throw new UnsupportedOperationException( "Not implemented" );
                                            }
                                        }
                                    };
                                    return (Query<T>) Proxy.newProxyInstance( uow.getClass().getClassLoader(),
                                                                              new Class[]{Query.class}, qHandler);
                                }
                            };
                        }
                    };
                }

                return method.invoke( uow, args );
            }
        };

        return (UnitOfWork) Proxy.newProxyInstance( uow.getClass().getClassLoader(), new Class[]{UnitOfWork.class}, handler);
    }

}
