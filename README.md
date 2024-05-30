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


### Локальный запуск 
1. Склонировать проект
   ```
   git clone https://github.com/gpb-it-factory/satvaldiev-telegram-bot.git
   ```
2. Перейти в корневую папку проекта ___satvaldiev-telegram-bot___
3. Выполнить команду в Windows PowerShell (вместо ___bot_name___ и ___bot_token___ прописать ваши креды):
   ```
   $Env:BOT_NAME = "bot_name"; $Env:BOT_TOKEN = "bot_token"; ./gradlew bootRun
   ```
4. Если через bash, то выполнить команду (вместо ___bot_name___ и ___bot_token___ прописать ваши креды): 
   ```
   BOT_NAME={bot_name} BOT_TOKEN={bot_token} ./gradlew bootRun
   ```


### Поддерживаемые команды бота
- ___/start___ (ответ - "Привет, firstName!")
- ___/stop___ (ответ - "Пока, firstName!")
- ___/ping___ (ответ - "pong")
- ___/register___ (ответ - "Поздравляем! Вы стали клиентом нашего банка")