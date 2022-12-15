package pe.edu.pucp.packrunner.config;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pe.edu.pucp.packrunner.services.DataFileService;

@Component
public class ScheduledTasks {

  private final String[] orderFilename = { "./data/inf226.ventas202206.txt", "./data/inf226.ventas202207.txt",
      "./data/inf226.ventas202208.txt", "./data/inf226.ventas202209.txt", "./data/inf226.ventas202210.txt",
      "./data/inf226.ventas202211.txt", "./data/inf226.ventas202212.txt", "./data/inf226.ventas202301.txt",
      "./data/inf226.ventas202302.txt", "./data/inf226.ventas202303.txt", "./data/inf226.ventas202304.txt",
      "./data/inf226.ventas202305.txt" };

  private static int i = 0;

  @Autowired
  DataFileService service;

  @Scheduled(fixedRate = 1000 * 60 * 60)
  public void registerFutureOrders() throws FileNotFoundException, InterruptedException {
    // if (i < 12) {
    // service.readOrderFileByName(orderFilename[i]);
    // i++;
    // }
  }
}
