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
package org.qi4j.chronos.ui.util;

import org.qi4j.chronos.ui.MockPage;
import org.qi4j.chronos.ui.WicketTestCase;

public class ValidatorUtilTest extends WicketTestCase
{
    public void testIsEmpty_true()
    {
        assertTrue( ValidatorUtil.isEmpty( "", "field", new MockPage() ) );
        assertTrue( ValidatorUtil.isEmpty( null, "field", new MockPage() ) );
    }

    public void testIsEmpty_false()
    {
        assertFalse( ValidatorUtil.isEmpty( "abc", "field", new MockPage() ) );
    }

    public void testIsNotInteger_true()
    {
        assertTrue( ValidatorUtil.isNotInteger( "abc", "field", new MockPage() ) );
    }

    public void testIsNotInteger_false()
    {
        assertFalse( ValidatorUtil.isNotInteger( "123", "field", new MockPage() ) );
    }

    public void testIsInvalidEmail_true()
    {
        assertTrue( ValidatorUtil.isNotValidEmail( "aa@gmailcom", "field", new MockPage() ) );
        assertTrue( ValidatorUtil.isNotValidEmail( "aaa.gmailcom", "field", new MockPage() ) );
        assertTrue( ValidatorUtil.isNotValidEmail( "a^%a@gmailcom", "field", new MockPage() ) );
    }

    public void testIsInvalidEmail_false()
    {
        assertFalse( ValidatorUtil.isNotValidEmail( "bp@gmail.com", "field", new MockPage() ) );
    }

    public void testIsInvalidLength_true()
    {
        assertTrue( ValidatorUtil.isInvalidLength( "abc", "field", 2, new MockPage() ) );
    }

    public void testIsInvalidLength_false()
    {
        assertFalse( ValidatorUtil.isInvalidLength( "abc", "field", 3, new MockPage() ) );
    }

    public void testIsEmptyOrInvalidLength_true()
    {
        assertTrue( ValidatorUtil.isEmptyOrInvalidLength( "", "field", 2, new MockPage() ) );
        assertTrue( ValidatorUtil.isEmptyOrInvalidLength( "abc", "field", 2, new MockPage() ) );
    }

    public void testIsEmptyOrInvalidLength_false()
    {
        assertFalse( ValidatorUtil.isEmptyOrInvalidLength( "abc", "field", 3, new MockPage() ) );
        assertFalse( ValidatorUtil.isEmptyOrInvalidLength( "abc", "field", 3, new MockPage() ) );
    }

}
