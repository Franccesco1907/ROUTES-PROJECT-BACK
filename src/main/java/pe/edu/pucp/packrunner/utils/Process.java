package pe.edu.pucp.packrunner.utils;

import org.springframework.beans.factory.annotation.Autowired;

import pe.edu.pucp.packrunner.dao.BlockDao;
import pe.edu.pucp.packrunner.models.Block;

public class Process extends Thread {
  Block b;

  BlockDao blockDao;

  @Override
  public void run() {
    blockDao.register(b);
  }

  public void setBlock(Block b) {
    this.b = b;
  }

  public void setBlockDao(BlockDao blockDao) {
    this.blockDao = blockDao;
  }

}
