/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.vtype;

import java.time.Instant;
import org.epics.util.array.ArrayInteger;
import org.epics.util.array.ListInteger;
import org.epics.util.array.ListNumber;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author carcassi
 * @param <L>
 * @param <V>
 */
public abstract class FeatureTestVNumberArray<L extends ListNumber, V extends VNumberArray> {
    
    abstract L getData();
    
    abstract V of(L data, Alarm alarm, Time time, Display display);
    
    abstract V of(L data, ListInteger sizes, Alarm alarm, Time time, Display display);
    
    abstract String getToString();

    @Test
    public void of1() {
        Alarm alarm = Alarm.of(AlarmSeverity.MINOR, AlarmStatus.DB, "LOW");
        Time time = Time.of(Instant.ofEpochSecond(1354719441, 521786982));
        ListInteger sizes = ArrayInteger.of(5,2);
        V value = of(getData(), sizes, alarm, time, Display.none());
        assertThat(value.getData(), equalTo(getData()));
        assertThat(value.getSizes(), equalTo(sizes));
        assertThat(value.getAlarm(), equalTo(alarm));
        assertThat(value.getTime(), equalTo(time));
        assertThat(value.toString(), equalTo(getToString()));
        
        value = of(getData(), alarm, time, Display.none());
        assertThat(value.getData(), equalTo(getData()));
        assertThat(value.getSizes(), equalTo(ArrayInteger.of(10)));
        assertThat(value.getAlarm(), equalTo(alarm));
        assertThat(value.getTime(), equalTo(time));
    }
    
    @Test(expected = NullPointerException.class)
    public void of2() {
        of(null, Alarm.none(), Time.now(), Display.none());
    }
    
    @Test(expected = NullPointerException.class)
    public void of3() {
        of(getData(), null, Time.now(), Display.none());
    }
    
    @Test(expected = NullPointerException.class)
    public void of4() {
        of(getData(), Alarm.none(), null, Display.none());
    }
    
    @Test(expected = NullPointerException.class)
    public void of5() {
        of(getData(), Alarm.none(), Time.now(), null);
    }
    
    @Test(expected = NullPointerException.class)
    public void of6() {
        of(null, ArrayInteger.of(10), Alarm.none(), Time.now(), Display.none());
    }
    
    @Test(expected = NullPointerException.class)
    public void of7() {
        of(getData(), ArrayInteger.of(10), null, Time.now(), Display.none());
    }
    
    @Test(expected = NullPointerException.class)
    public void of8() {
        of(getData(), ArrayInteger.of(10), Alarm.none(), null, Display.none());
    }
    
    @Test(expected = NullPointerException.class)
    public void of9() {
        of(getData(), ArrayInteger.of(10), Alarm.none(), Time.now(), null);
    }
    
    @Test(expected = NullPointerException.class)
    public void of10() {
        of(getData(), null, Alarm.none(), Time.now(), Display.none());
    }
    
}
