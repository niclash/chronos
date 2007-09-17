///*
// * Copyright 2007 Lan Boon Ping. All Rights Reserved.
// * Copyright 2007 Sianny Halim. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
//*/
//package org.qi4j.chronos.model.modifiers;
//
//import java.util.Date;
//import org.qi4j.api.annotation.scope.Modifies;
//import org.qi4j.api.annotation.ThisAs;
//import org.qi4j.chronos.model.TimeRange;
//import org.qi4j.library.general.model.ValidationException;
//
//public final class TimeRangeValidationModifier implements TimeRange
//{
//    @ThisAs TimeRange meAsTimeRange;
//    @Modifies TimeRange next;
//
//    public Date getStartTime()
//    {
//        return next.getStartTime();
//    }
//
//    public void setStartTime( Date startTime )
//    {
//        if( startTime == null )
//        {
//            throw new ValidationException( "Invalid time range! Start Time is null " );
//        }
//
//        Date endTime = meAsTimeRange.getEndTime();
//
//        checkTimeRange( startTime, endTime );
//
//        next.setStartTime( startTime );
//    }
//
//    public Date getEndTime()
//    {
//        return next.getEndTime();
//    }
//
//    public void setEndTime( Date endTime )
//    {
//        if( endTime == null )
//        {
//            throw new ValidationException( "Invalid time range! End Time is null " );
//        }
//        Date startTime = meAsTimeRange.getStartTime();
//
//        checkTimeRange( startTime, endTime );
//
//        next.setEndTime( endTime );
//    }
//
//    private void checkTimeRange( Date startTime, Date endTime )
//    {
//        if( endTime != null && endTime.before( startTime ) )
//        {
//            throw new ValidationException( "Invalid time range! End Time[" + endTime + "] " +
//                                           "must be after Start Time[" + startTime + "]!" );
//        }
//    }
//}
