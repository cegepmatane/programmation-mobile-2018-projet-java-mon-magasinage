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
import android.widget.ListView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ca.qc.cgmatane.informatique.monmagasinage.ListeCourse;

import static java.nio.file.Paths.get;

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

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"channel1")
                .setContentTitle("titre")
                .setContentText("text")
                .setSmallIcon(android.support.v4.R.drawable.notification_icon_background)
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
