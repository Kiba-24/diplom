# Интеллектуальная система техподдержки

Full-stack приложение для обработки обращений в техподдержку с AI-чат-ботом.

## Стек
- Backend: Java 17, Spring Boot 3, Spring Security, JPA
- Frontend: React + Vite
- DB: PostgreSQL
- Контейнеризация: Docker Compose

## Функции
- Регистрация и логин операторов, bcrypt хеширование.
- CRUD обращений: создание, список, фильтры/поиск/сортировка, архивация.
- Импорт/экспорт CSV и отчет по статусам.
- Модуль чат-бота: первичная классификация интента (пароль, VPN, общее).
- Предзагрузка 50 тестовых обращений.

## Запуск
```bash
docker compose up --build
```

Frontend: http://localhost:5173  
Backend: http://localhost:8080

## Диаграммы
- UML: `docs/uml.md`
- ER + нормализация: `docs/database.md`
- Блок-схемы (ГОСТ): `docs/algorithms.md`
