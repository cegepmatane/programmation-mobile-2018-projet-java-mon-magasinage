package ca.qc.cgmatane.informatique.monmagasinage.modele;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import ca.qc.cgmatane.informatique.monmagasinage.ListeCourse;
import ca.qc.cgmatane.informatique.monmagasinage.R;
import ca.qc.cgmatane.informatique.monmagasinage.vue.VueFaireCourse;

import java.time.format.DateTimeFormatter;

public class Notification extends JobService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

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
         int id = (int) bundle.get("id");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"channel1")
                .setSmallIcon(R.mipmap.mon_magasinage_icon_circle)
                .setBadgeIconType(R.mipmap.mon_magasinage_icon_circle)
                .setContentTitle(titre)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        Intent notificationIntent = new Intent(getApplicationContext(), VueFaireCourse.class);
        notificationIntent.putExtra(Course.CHAMP_ID_COURSE, Integer.toString(id));

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(notificationIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(R.mipmap.mon_magasinage_icon_circle,"click",resultPendingIntent);
        manager.notify(1,builder.build());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
