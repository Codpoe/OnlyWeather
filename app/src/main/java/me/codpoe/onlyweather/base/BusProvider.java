package me.codpoe.onlyweather.base;

import com.squareup.otto.Bus;

/**
 * Created by Codpoe on 2016/5/24.
 */
public class BusProvider {
    private volatile static Bus bus = null;

    private BusProvider() {
    }

    public static Bus getInstance() {
        if (bus == null) {
            synchronized (BusProvider.class) {
                bus = new Bus();
            }
        }
        return bus;
    }
}
