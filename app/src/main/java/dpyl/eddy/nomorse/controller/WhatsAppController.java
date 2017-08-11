package dpyl.eddy.nomorse.controller;

import java.util.HashSet;
import java.util.Set;

import dpyl.eddy.nomorse.Constants;
import dpyl.eddy.nomorse.model.WhatsAppNotification;

public class WhatsAppController {

    private static final long MARGIN = 1000L; // Minimum time in millis between activations

    private static WhatsAppController instance;

    private Set<WhatsAppNotification> oldNotifications;
    private Set<WhatsAppNotification> newNotifications;
    private WhatsAppNotification lastNotification;

    private WhatsAppController() {
        oldNotifications = new HashSet<>();
        newNotifications = new HashSet<>();
    }

    static WhatsAppController init() {
        if (instance == null) instance = new WhatsAppController();
        return instance;
    }

    boolean notify(WhatsAppNotification wan) {
        if (getNewest() != null && wan.getPostTime() - getNewest().getPostTime() > Constants.PERIOD) flush();
        // Even if two or more messages come one after another, from different senders, there's no reason to notify again so soon.
        if (wan.getPostTime() - lastNotification.getPostTime() > MARGIN) {
            if (!newNotifications.contains(wan)) {
                if (!oldNotifications.contains(wan)) {
                    // It's almost certain {@param wan} was the latest to be received.
                    return true;
                } else {
                    // in this case, we can't be sure. This will introduce small inconsistencies in behaviour and should be fixed.
                    return true;
                }
            }
        }
        lastNotification = wan;
        newNotifications.add(wan);
        return false;
    }

    boolean isGroup(WhatsAppNotification wan) {
        return wan.getTag().split(".").length == 2;
    }

    private WhatsAppNotification getNewest() {
        WhatsAppNotification res = null;
        for (WhatsAppNotification wan : newNotifications) {
            if (res == null || res.getPostTime() < wan.getPostTime()) res = wan;
        } return res;
    }

    private void flush() {
        oldNotifications = new HashSet<>(newNotifications);
        newNotifications = new HashSet<>();
    }

}
