package pro.sky.telegrambot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class Scheduler {
    private final NotificationTaskRepository notificationTaskRepository;
    private final TelegramBot telegramBot;

    public Scheduler(NotificationTaskRepository notificationTaskRepository, TelegramBot telegramBot) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        Collection<NotificationTask> tasks = notificationTaskRepository.findByTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        ScheduleNotification(tasks);
    }

    public void ScheduleNotification(Collection<NotificationTask> task ) {
        task.forEach(taskNew -> {
                    telegramBot.execute(new SendMessage(taskNew.getChatId(),
                            taskNew.getTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                                    + " " + taskNew.getMessage()));
                });
    }
}

