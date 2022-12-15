package pe.edu.pucp.packrunner.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import pe.edu.pucp.packrunner.services.TickService;

@Configuration
@EnableScheduling
public class DynamicSchedulingConfig implements SchedulingConfigurer {

  @Autowired
  private TickService tickService;

  @Bean
  public Executor taskExecutor() {
    return Executors.newSingleThreadScheduledExecutor();
  }


  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
      /*
    taskRegistrar.setScheduler(taskExecutor());
    taskRegistrar.addTriggerTask(
        new Runnable() {
          @Override
          public void run() {
            tickService.tick();
          }
        },
        new Trigger() {
          @Override
          public Date nextExecutionTime(TriggerContext context) {
            Optional<Date> lastCompletionTime = Optional.ofNullable(context.lastCompletionTime());
            Instant nextExecutionTime = lastCompletionTime.orElseGet(Date::new).toInstant()
                .plusMillis(tickService.getDelay());
            return Date.from(nextExecutionTime);
          }
        });

       */
  }
}
