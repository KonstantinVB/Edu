package ru.otus.hwork04;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * Получаем данные для анализа эффективности GC
 */
public class GCViewer {
    public void run() {
        long sTime = System.currentTimeMillis();
        final List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        gcMXBeans.forEach(gcBean -> {
            final NotificationEmitter emitter = (NotificationEmitter) gcBean;
            final NotificationListener listener = (notification, handback) -> {
                if (GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION.equals(notification.getType())) {
                    final GcInfo gcInfo = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData()).getGcInfo();
                    double spentTime = gcBean.getCollectionTime();
                    double totalspentTime = System.currentTimeMillis()-sTime;
                    double spentTimeRatio = spentTime/totalspentTime;
                    System.out.println(gcBean.getName() + ": GCTime/TotalTime   :" + spentTimeRatio);
                    System.out.println(gcBean.getName() + ": average GCTime, ms :" + gcBean.getCollectionTime()/gcBean.getCollectionCount());
                    System.out.println(gcBean.getName() + ": GCDuration, ms     :" + gcInfo.getDuration());
                }
            };

            emitter.addNotificationListener(listener, null, null);
        });
    }
}
