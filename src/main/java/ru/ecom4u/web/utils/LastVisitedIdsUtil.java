package ru.ecom4u.web.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Evgeny(e299792459@gmail.com) on 16.03.14.
 */
public class LastVisitedIdsUtil {

    public static Set<Integer> parceIds(String lastIds) {
        Set<Integer> ids = new HashSet<Integer>();
        if (null != lastIds) {
            for (String id : lastIds.split(":")) {
                try {
                    ids.add(Integer.parseInt(id));
                } catch (Throwable t) {
                    System.err.println("" + t.getMessage());
                }
            }
        }
        return ids;
    }

    public static String createIdsString(Set<Integer> ids) {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (Integer id : ids) {
            if (first) {
                sb.append(id);
                first = false;
            } else {
                sb.append(':');
                sb.append(id);
            }
        }
        return sb.toString();
    }
}
