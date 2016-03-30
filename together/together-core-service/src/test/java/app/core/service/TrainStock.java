/**
 * Alipay.com Inc. Copyright (c) 2004-2016 All Rights Reserved.
 */
package app.core.service;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Pengju.zpj
 * @version $Id: TrainStock.java, v 0.1 2016年3月3日 下午3:47:01 Pengju.zpj Exp $
 */
public class TrainStock {

    public Map<Integer, Integer> stationMap     = new HashMap<Integer, Integer>();
    public BitSet                canBookIndex;
    public Map<Integer, Integer> s2sMaxCountMap = new HashMap<Integer, Integer>();
    public Integer[]             s2sTickets;
    public BitSet[]              seatCanBookIndex;

    @Override
    public String toString() {
        String temp = "stationMap:" + stationMap.toString() + "\r\n";
        temp = temp + "s2sTickets:";
        for (int i = 0; i < s2sTickets.length; i++) {
            temp = temp + "," + s2sTickets[i];
        }
        temp = temp + "\r\n";
        temp = temp + "canBookIndex:" + canBookIndex.toString() + "\r\n";

        temp = temp + "s2sMaxCountMap:" + s2sMaxCountMap.toString() + "\r\n";

        temp = temp + "seatCanBookIndex:";
        for (int i = 0; i < seatCanBookIndex.length; i++) {
            temp = temp + "," + seatCanBookIndex[i].toString();
        }
        temp = temp + "\r\n";
        return temp;
    }

    public Integer bookTicket(Integer fromStation, Integer toStation) {
        Integer fIdx = stationMap.get(fromStation);
        Integer tIdx = stationMap.get(toStation);
        BitSet bookIndex = this.canBookIndex.get(fIdx, tIdx);
        if (bookIndex.isEmpty()) {
            Integer maxCount = s2sMaxCountMap.get(fromStation * 10000 + toStation);
            if (maxCount != null && maxCount > 0 || maxCount == null) {
                for (int i = 0; i < this.seatCanBookIndex.length; i++) {
                    BitSet seatBookIndex = this.seatCanBookIndex[i].get(fIdx, tIdx);
                    if (seatBookIndex.isEmpty()) {
                        for (int j = fIdx; j < tIdx; j++) {
                            int temp1 = s2sTickets[j] - 1;
                            s2sTickets[j] = temp1;
                            if (temp1 == 0) {
                                canBookIndex.set(j);
                            }
                            this.seatCanBookIndex[i].set(j);
                        }
                        if (maxCount != null) {
                            this.s2sMaxCountMap.put(fromStation * 10000 + toStation, maxCount - 1);
                        }
                        return i;
                    }
                }
                return -101;
            } else {
                return -102;
            }
        } else {
            return -100;
        }
    }

    public Integer cancleTicket(Integer fromStation, Integer toStation, int seatIndex) {
        Integer fIdx = stationMap.get(fromStation);
        Integer tIdx = stationMap.get(toStation);
        for (int j = fIdx; j < tIdx; j++) {
            int temp1 = s2sTickets[j] + 1;
            s2sTickets[j] = temp1;
            if (temp1 == 0) {
                canBookIndex.set(j);
            }
            this.seatCanBookIndex[seatIndex].set(j, false);
        }
        Integer maxCount = this.s2sMaxCountMap.get(fromStation * 10000 + toStation);
        if (maxCount != null) {
            this.s2sMaxCountMap.put(fromStation * 10000 + toStation, maxCount + 1);
        }
        return 1;
    }

    public Integer getStock(Integer fromStation, Integer toStation) {
        Integer fIdx = this.stationMap.get(fromStation);
        Integer tIdx = this.stationMap.get(toStation);
        int temp1 = 10000;
        for (int j = fIdx; j < tIdx; j++) {
            if (temp1 > s2sTickets[j]) {
                temp1 = s2sTickets[j];
            }
        }
        return temp1;
    }
}
