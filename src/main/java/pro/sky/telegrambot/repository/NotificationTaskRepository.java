package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.NotificationTask;

import java.time.LocalDateTime;

public interface NotificationTaskRepository extends JpaRepository <NotificationTask, Integer> {
    NotificationTask findByTime (LocalDateTime dateTime);
}
