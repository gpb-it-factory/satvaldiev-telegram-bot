# Telegram-бот
Telegram-бот является Frontend компонентом проекта "Мини-банк". Бот выполняет функцию клиентского приложения, 
отправляя запросы пользователей к Java-сервису (второму компоненту проекта)

```plantuml
@startuml
skinparam backgroundColor #EEEBDC

skinparam sequence {
ArrowColor DeepSkyBlue
ArrowFontSize 18
ArrowFontColor darkviolet
ArrowFontName Impact
LifeLineBorderColor blue

ParticipantBorderColor DeepSkyBlue
ParticipantBackgroundColor DodgerBlue
ParticipantFontName Impact
ParticipantFontSize 25
ParticipantFontColor #EEEBDC
}
participant Client
participant Frontend 
participant Middle 
participant Backend 


Client -> Frontend: HTTP
Frontend -> Middle: HTTP
Middle -> Backend: HTTP
Backend --> Middle: HTTP
Middle --> Frontend: HTTP
Frontend --> Client: HTTP
@enduml
```

![Image](image.png)

### Подготовка к запуску бота
1. Склонировать проект
2. Внести имя и токен вашего Telegram бота в src/main/resources/application.yml
### Локальный запуск через Intellij Idea
1. Выбрать файл src/main/java/ru/satvaldiev/telegrambot/TelegramBotApplication.java
2. Нажать зеленый треугольник слева от имени класса
### Локальный запуск через консоль
1. Перейти в корневую папку проекта
2. Выполнить команды ___./gradlew --stop___ и ___./gradlew clean build___
3. Перейти в папку build/libs/
4. Выполнить команду ___java -jar TelegramBot-0.0.1-SNAPSHOT.jar___
### Поддерживаемые команды бота
- ___/start___ (ответ - "Привет, firstName!")
- ___/stop___ (ответ - "Пока, firstName!")
- ___/ping___ (ответ - "pong")