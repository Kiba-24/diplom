# UML (PlantUML)
```plantuml
@startuml
!define RECTANGLE class

actor "Пользователь" as User
actor "Оператор" as Operator

rectangle "Frontend (React)" as FE
rectangle "Backend (Spring Boot)" as BE {
  rectangle "REST Controllers"
  rectangle "Service Layer"
  rectangle "SLA Module"
  rectangle "AI Module"
  rectangle "Security (AD Integration)"
}

database "PostgreSQL" as DB
rectangle "Active Directory" as AD
rectangle "LLM / AI Engine" as LLM

User --> FE
Operator --> FE

FE --> BE : REST API

BE --> DB : SQL
BE --> AD : LDAP
BE --> LLM : AI запросы

@enduml
```
Архитектура построена по принципу client-server
Backend реализован на Spring Boot
Используется реляционная СУБД
Интеграция с AD через LDAP
Интеллектуальный модуль не взаимодействует напрямую с пользователем
