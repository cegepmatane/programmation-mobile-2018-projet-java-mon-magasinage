package ca.qc.cgmatane.informatique.monmagasinage.modele;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import ca.qc.cgmatane.informatique.monmagasinage.ListeCourse;
import ca.qc.cgmatane.informatique.monmagasinage.R;

import java.time.format.DateTimeFormatter;

public class Notification extends JobService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Log.d("notif", "start schedule notif");


        PendingIntent contentPendingIntent = PendingIntent.getActivity(
                this,0, new Intent(this, ListeCourse.class), PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel channel1 = new NotificationChannel(
                "channel1",
                "channel1",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel1.setDescription("channel1");

        manager.createNotificationChannel(channel1);

         PersistableBundle bundle = jobParameters.getExtras();
         String titre = (String) bundle.get("titre");
         String text = (String) bundle.get("text");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"channel1")
                .setSmallIcon(R.mipmap.mon_magasinage_icon_circle)
                .setBadgeIconType(R.mipmap.mon_magasinage_icon_circle)
                .setContentTitle(titre)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        manager.notify(1,builder.build());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
