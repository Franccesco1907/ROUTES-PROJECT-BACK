package pe.edu.pucp.packrunner.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.pucp.packrunner.dao.BlockDao;
import pe.edu.pucp.packrunner.dao.EdgeDao;
import pe.edu.pucp.packrunner.models.Block;
import pe.edu.pucp.packrunner.models.Edge;

@Service
public class TickService {
  @Autowired
  BlockDao blockDao;

  @Autowired
  EdgeDao edgeDao;

  public void tick() {
    List<Block> blocks = blockDao.getAll(null, null);
    Date cur = new Date();
    long min = Long.MAX_VALUE;
    Block bMin = new Block();
    boolean lock = true;
    for (Block b : blocks) {
      Date start = b.getStartDate();
      Date end = b.getEndDate();
      long dif;
      if ((cur.getTime() < start.getTime())) {
        dif = start.getTime() - cur.getTime();
        if (Math.abs(dif) < min) {
          min = dif;
          bMin = b;
          lock = true;
        }
      } else if ((cur.getTime() < end.getTime())) {
        dif = end.getTime() - cur.getTime();
        if (Math.abs(dif) < min) {
          min = dif;
          bMin = b;
          lock = false;
        }
      }
    }
    Edge e = bMin.getEdge();
    if (lock)
      e.setBlocked(true);
    else
      e.setBlocked(false);
    edgeDao.update(e);
  }

  public long getDelay() {
    List<Block> blocks = blockDao.getAll(null, null);
    Date cur = new Date();
    long min = Long.MAX_VALUE;
    for (Block b : blocks) {
      Date start = b.getStartDate();
      Date end = b.getEndDate();
      long dif;
      if ((cur.getTime() < start.getTime())) {
        dif = start.getTime() - cur.getTime();
        if (dif < min)
          min = dif;
      } else if ((cur.getTime() < end.getTime())) {
        dif = end.getTime() - cur.getTime();
        if (dif < min)
          min = dif;
      }
    }
    return min;
  }

}
