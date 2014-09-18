package com.saviour.poweryoga.util;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author TalakB
 */
public class NotificationUtil {

    public static Flash flashScope() {
        return (FacesContext.getCurrentInstance().getExternalContext().getFlash());
    }

}
