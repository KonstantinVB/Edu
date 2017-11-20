package ru.otus.hwork04;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

import static java.lang.Thread.currentThread;

/*
 * Получаем данные для анализа эффективности GC
 */
public class GCViewer {
    private static final String[] statarray = new String[6];
    private static final Thread cThread = currentThread();
    public void run() {
        long stime = System.currentTimeMillis();
        System.out.println("Start time: " + new java.util.Date(stime));
        Thread t = new PrnStat();
        t.start();
        final List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        gcMXBeans.forEach(gcBean -> {
            final NotificationEmitter emitter = (NotificationEmitter) gcBean;
            final NotificationListener listener = (notification, handback) -> {
                if (GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION.equals(notification.getType())) {
                    double spenttime = System.currentTimeMillis() - stime;
                    Double countpermin =  60_000 * gcBean.getCollectionCount() / spenttime;
                    Double wtpermin = 60_000 * gcBean.getCollectionTime() / spenttime;
                    if (statarray[0] == null || statarray[0].contains(gcBean.getName())) {
                        statarray[0] = "GC_name         :"+gcBean.getName();
                        statarray[1] = "GC_count(/min)  :"+countpermin.longValue();
                        statarray[2] = "GC_wt(ms/min)   :"+wtpermin.longValue();
                    } else if (statarray[3] == null || statarray[3].contains(gcBean.getName())) {
                        statarray[3] = "GC_name         :"+gcBean.getName();
                        statarray[4] = "GC_count(/min)  :"+countpermin.longValue();
                        statarray[5] = "GC_wt(ms/min)   :"+wtpermin.longValue();
                    }
                }
            };
                emitter.addNotificationListener(listener, null, null);
        });
    }

    private static class PrnStat extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    for (int i = 0; i<6; i++) {
                        if (statarray[i] != null) {
                            System.out.println(statarray[i]);
                        }
                    }
                    System.out.println("- - - - - - - - - - - - - - - -");
                    if (cThread.isAlive()) {
                        Thread.sleep(60 * 1000);
                    } else {
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
