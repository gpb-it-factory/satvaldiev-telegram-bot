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