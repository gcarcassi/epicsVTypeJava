/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.vtype;

/**
 *
 * @author carcassi
 */
public class VShortTest extends FeatureTestVNumber<Short, VShort> {

    @Override
    Short getValue() {
        return 1;
    }

    @Override
    VShort of(Short value, Alarm alarm, Time time, Display display) {
        return VShort.of(value, alarm, time, display);
    }

    @Override
    String getToString() {
        return "VShort[1, MINOR(DB) - LOW, 2012-12-05T14:57:21.521786982Z]";
    }
    
}
