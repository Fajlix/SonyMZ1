package com.example.sonymz1;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EndDate implements ChallengeComponent{

    private long endDate;

    public EndDate(long endDate) {
        this.endDate = endDate;
    }
    public EndDate() {
        this.endDate = new Date().getTime();
    }

    /**
     *
     * @return returns the time left until the challenge ends.
     */
    public String getValue()
    {
        Date now = new Date();
        long currentDate = now.getTime();
        long ms = endDate - currentDate;
        int days = (int)TimeUnit.MILLISECONDS.toDays(ms);
        int hours =(int)(TimeUnit.MILLISECONDS.toHours(ms) - TimeUnit.DAYS.toHours(days));
        int minutes = (int)(TimeUnit.MILLISECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms)));
        int seconds = (int)(TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
        return String.format(Locale.getDefault(),"%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
    }
}
