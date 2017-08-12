package dpyl.eddy.nomorse.controller;

import java.util.HashSet;
import java.util.Set;

import dpyl.eddy.nomorse.Constants;
import dpyl.eddy.nomorse.model.WhatsAppNotification;

class WhatsAppController {

    private static final long MARGIN = 1000L; // Minimum time in millis between activations

    private static WhatsAppController instance;

    private Set<WhatsAppNotification> oldNotifications;
    private Set<WhatsAppNotification> newNotifications;
    private WhatsAppNotification lastNotification;

    private WhatsAppController() {
        oldNotifications = new HashSet<>();
        newNotifications = new HashSet<>();
    }

    static WhatsAppController getInstante() {
        if (instance == null)
            synchronized (WhatsAppController.class) {
                if (instance == null) {
                    instance = new WhatsAppController();
                }
            }
        return instance;
    }

    boolean notify(WhatsAppNotification wan) {
        boolean res = false;
        if (lastNotification != null && wan.getPostTime() - lastNotification.getPostTime() > Constants.PERIOD) flush();
        // Even if two or more messages come one after another, from different senders, there's no reason to notify again so soon.
        if (lastNotification == null || wan.getPostTime() - lastNotification.getPostTime() > MARGIN) {
            if (!newNotifications.contains(wan)) {
                if (!oldNotifications.contains(wan)) {
                    // It's almost certain {@param wan} was the latest to be received.
                    res = true;
                } else {
                    // In this case, we can't be sure. This will introduce small inconsistencies in behaviour and should be fixed.
                    res = true;
                }
            }
        }
        lastNotification = wan;
        // We need to update the postTime of {@param wan} if it's already in the Set
        newNotifications.remove(wan);
        newNotifications.add(wan);
        return res;
    }

    boolean isGroup(WhatsAppNotification wan) {
        return wan.getTag().split(".").length == 2;
    }

    private void flush() {
        oldNotifications = new HashSet<>(newNotifications);
        newNotifications = new HashSet<>();
    }

}
