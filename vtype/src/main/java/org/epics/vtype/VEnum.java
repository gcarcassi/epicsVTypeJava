/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.vtype;

import java.util.List;

/**
 * Scalar enum with alarm and timestamp.
 * Given that enumerated values are of very limited use without
 * the labels, and that the current label is the data most likely used, the
 * enum is scalar of type {@link String}. The index is provided as an extra field, and
 * the list of all possible values is always provided.
 *
 * @author carcassi
 */
public abstract class VEnum extends Scalar {
    
    /**
     * {@inheritDoc }
     */
    @Override
    public abstract String getValue();

    /**
     * Return the index of the value in the list of labels.
     *
     * @return the current index
     */
    public abstract int getIndex();
    
    /**
     * Returns the display information, including all possible choice names.
     * 
     * @return the enum display
     */
    public abstract EnumDisplay getDisplay();

    /**
     * Create a new VEnum.
     * 
     * @param index the index in the label array
     * @param metaData the metadata
     * @param alarm the alarm
     * @param time the time
     * @return the new value
     */
    public static VEnum of(int index, EnumDisplay metaData, Alarm alarm, Time time) {
        return new IVEnum(index, metaData, alarm, time);
    }

}
