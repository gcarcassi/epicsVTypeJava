/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.vtype;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.epics.util.array.CollectionNumbers;
import org.epics.util.array.ListNumber;
import org.epics.util.array.ListNumbers;

/**
 * Tag interface to mark all the members of the value classes.
 *
 * @author carcassi
 */
public abstract class VType {
    
//    private static Collection<Class<?>> types = Arrays.<Class<?>>asList(VByte.class, VByteArray.class, VDouble.class,
//            VDoubleArray.class, VEnum.class, VEnumArray.class, VFloat.class, VFloatArray.class,
//            VLong.class, VLongArray.class, VInt.class, VIntArray.class, VMultiDouble.class, VMultiEnum.class,
//            VMultiInt.class, VMultiString.class, VShort.class, VShortArray.class,
//            VStatistics.class, VString.class, VStringArray.class, VBoolean.class, VBooleanArray.class, VTable.class,
//            VImage.class);
    private static final Collection<Class<?>> types = Arrays.<Class<?>>asList(
            VByte.class,
            VUByte.class,
            VShort.class,
            VUShort.class,
            VInt.class,
            VUInt.class,
            VLong.class,
            VULong.class,
            VFloat.class,
            VDouble.class,
            VEnum.class,
            VString.class,
            VByteArray.class,
            VUByteArray.class,
            VShortArray.class,
            VUShortArray.class,
            VIntArray.class,
            VUIntArray.class,
            VLongArray.class,
            VULongArray.class,
            VFloatArray.class,
            VDoubleArray.class);

    /**
     * Returns the type of the object by returning the class object of one
     * of the VXxx interfaces. The getClass() methods returns the
     * concrete implementation type, which is of little use. If no
     * super-interface is found, Object.class is used.
     * 
     * @param obj an object implementing a standard type
     * @return the type is implementing
     */
    public static Class<?> typeOf(Object obj) {
        if (obj == null)
            return null;
        
        for (Class<?> type : types) {
            if (type.isInstance(obj)) {
                return type;
            }
        }

        return Object.class;
    }
    
    /**
     * Converts a standard java type to VTypes. Returns null if no conversion
     * is possible. Calls {@link #toVType(java.lang.Object, org.epics.vtype.Alarm, org.epics.vtype.Time, org.epics.vtype.Display) } 
     * with no alarm, time now and no display.
     * 
     * @param javaObject the value to wrap
     * @return the new VType value
     */
    public static VType toVType(Object javaObject) {
        return toVType(javaObject, Alarm.none(), Time.now(), Display.none());
    }
    
    /**
     * Converts a standard java type to VTypes. Returns null if no conversion
     * is possible. Calls {@link #toVType(java.lang.Object, org.epics.vtype.Alarm, org.epics.vtype.Time, org.epics.vtype.Display) } 
     * with the given alarm, time now and no display.
     * 
     * @param javaObject the value to wrap
     * @param alarm the alarm
     * @return the new VType value
     */
    public static VType toVType(Object javaObject, Alarm alarm) {
        return toVType(javaObject, alarm, Time.now(), Display.none());
    }
    
    /**
     * Converts a standard java type to VTypes. Returns null if no conversion
     * is possible.
     * <p>
     * Types are converted as follow:
     * <ul>
     *   <li>Boolean -&gt; VBoolean</li>
     *   <li>Number -&gt; corresponding VNumber</li>
     *   <li>String -&gt; VString</li>
     *   <li>number array -&gt; corresponding VNumberArray</li>
     *   <li>ListNumber -&gt; corresponding VNumberArray</li>
     *   <li>List -&gt; if all elements are String, VStringArray</li>
     * </ul>
     * 
     * @param javaObject the value to wrap
     * @param alarm the alarm
     * @param time the time
     * @param display the display
     * @return the new VType value
     */
    public static VType toVType(Object javaObject, Alarm alarm, Time time, Display display) {
        if (javaObject instanceof Number) {
            return VNumber.of((Number) javaObject, alarm, time, display);
        } else if (javaObject instanceof String) {
            return VString.of((String) javaObject, alarm, time);
        } else if (javaObject instanceof Boolean) {
            return null;//newVBoolean((Boolean) javaObject, alarm, time);
        } else if (javaObject instanceof byte[]
                || javaObject instanceof short[]
                || javaObject instanceof int[]
                || javaObject instanceof long[]
                || javaObject instanceof float[]
                || javaObject instanceof double[]) {
            return VNumberArray.of(CollectionNumbers.toList(javaObject), alarm, time, display);
        } else if (javaObject instanceof ListNumber) {
            return VNumberArray.of((ListNumber) javaObject, alarm, time, display);
        } else if (javaObject instanceof String[]) {
            return null;//newVStringArray(Arrays.asList((String[]) javaObject), alarm, time);
        } else if (javaObject instanceof List) {
            boolean matches = true;
            List list = (List) javaObject;
            for (Object object : list) {
                if (!(object instanceof String)) {
                    matches = false;
                }
            }
            if (matches) {
                @SuppressWarnings("unchecked")
                List<String> newList = (List<String>) list;
                return null;//newVStringArray(Collections.unmodifiableList(newList), alarm, time);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    static void argumentNotNull(String argName, Object value) {
        if (value == null) {
            throw new NullPointerException(argName + " can't be null");
        }
    }
}
