/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.vtype;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;
import org.epics.util.stats.Range;

/**
 * Limit and unit information needed for display and control.
 * <p>
 * The numeric limits are given in double precision no matter which numeric
 * type. The unit is a simple String, which can be empty if no unit information
 * is provided. The number format can be used to convert the value to a String.
 *
 * @author carcassi
 */
public abstract class Display {

    /**
     * The range for the value when displayed.
     * 
     * @return the display range; can be Range.UNDEFINED but not null
     */
    public abstract Range getDisplayRange();

    /**
     * The range for the alarm associated to the value.
     * 
     * @return the alarm range; can be Range.UNDEFINED but not null
     */
    public abstract Range getAlarmRange();

    /**
     * The range for the warning associated to the value.
     * 
     * @return the warning range; can be Range.UNDEFINED but not null
     */
    public abstract Range getWarningRange();

    /**
     * The range used for changing the value.
     * 
     * @return the control range; can be Range.UNDEFINED but not null
     */
    public abstract Range getControlRange();

    /**
     * String representation of the unit using for all values.
     * Never null. If not available, returns the empty String.
     *
     * @return unit
     */
    public abstract String getUnit();

    /**
     * Returns a NumberFormat that creates a String with just the value (no units).
     * Format is locale independent and should be used for all values (values and
     * min/max of the ranges). Never null.
     *
     * @return the default format for all values
     */
    public abstract NumberFormat getFormat();

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
	if (obj instanceof Display) {
            Display other = (Display) obj;
        
            return Objects.equals(getFormat(), other.getFormat()) &&
                Objects.equals(getUnit(), other.getUnit()) &&
                Objects.equals(getDisplayRange(), other.getDisplayRange()) &&
                Objects.equals(getAlarmRange(), other.getAlarmRange()) &&
                Objects.equals(getWarningRange(), other.getWarningRange()) &&
                Objects.equals(getControlRange(), other.getControlRange());
        }
        
        return false;
    }

    @Override
    public final int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(getFormat());
        hash = 59 * hash + Objects.hashCode(getUnit());
        hash = 59 * hash + Objects.hashCode(getDisplayRange());
        hash = 59 * hash + Objects.hashCode(getAlarmRange());
        hash = 59 * hash + Objects.hashCode(getWarningRange());
        hash = 59 * hash + Objects.hashCode(getControlRange());
        return hash;
    }
    
    /**
     * Creates a new display.
     * 
     * @param displayRange the display range
     * @param warningRange the warning range
     * @param alarmRange the alarm range
     * @param controlRange the control range
     * @param units the units
     * @param numberFormat the preferred number format
     * @return a new display
     */
    public static Display of(final Range displayRange, final Range warningRange,
            final Range alarmRange, final Range controlRange,
            final String units, final NumberFormat numberFormat) {
        return new IDisplay(displayRange, warningRange, alarmRange,
                controlRange, units, numberFormat);
    }
    
    private static final Display DISPLAY_NONE = of(Range.undefined(),
            Range.undefined(), Range.undefined(), Range.undefined(), 
            "", new DecimalFormat());
    
    /**
     * Empty display information.
     * 
     * @return no display
     */
    public static Display none() {
        return DISPLAY_NONE;
    }
    
    /**
     * Null and non-VType safe utility to extract display information.
     * <ul>
     * <li>If the value has a display, the associated display is returned.</li>
     * <li>If the value has no display, {@link #none()} is returned.</li>
     * <li>If the value is null, {@link #none()} is returned.</li>
     * </ul>
     *
     * @param value the value
     * @return the display information for the value
     */
    public static Display displayOf(Object value) {
        if (value instanceof DisplayProvider) {
            return ((DisplayProvider) value).getDisplay();
        }
        
        return none();
    }
}
