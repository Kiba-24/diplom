# ER-диаграмма
```mermaid
erDiagram
  USERS ||--o{ TICKET : requester
  USERS ||--o{ TICKET : operator
  CATEGORY ||--o{ TICKET : classifies
  TICKET ||--o{ CHAT_MESSAGE : contains
```

Нормализация до 3НФ соблюдена: пользовательские данные, категории и сообщения вынесены в отдельные сущности.
