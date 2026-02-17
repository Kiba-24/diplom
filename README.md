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

## Быстрый запуск (Docker)
## Запуск
```bash
docker compose up --build
```

Frontend: http://localhost:5173  
Backend: http://localhost:8080

## Windows

### 1) Запуск проекта
Из корня проекта:
```powershell
docker compose up --build
```

### 2) Если видите ошибку `open //./pipe/dockerDesktopLinuxEngine`
Это означает, что Docker Engine не запущен.

Что делать:
1. Перезапустите Docker Desktop.
2. Проверьте WSL2:
```powershell
wsl --status
wsl -l -v
```
3. В Docker Desktop включите:
   - **Settings → General → Use the WSL 2 based engine**
   - **Settings → Resources → WSL Integration**


### 3) Проверка, что всё запустилось
```powershell
docker compose ps
docker compose logs -f backend
docker compose logs -f frontend
```


## Подключение к PostgreSQL

Приложение уже настроено на PostgreSQL по умолчанию.

### Параметры подключения backend
- `SPRING_DATASOURCE_URL` (пример: `jdbc:postgresql://localhost:5432/support`)
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

### Локальный запуск через Docker Compose
По умолчанию поднимается контейнер `db` (PostgreSQL 16), а backend подключается к нему по адресу `db:5432`.

При необходимости можно переопределить параметры через переменные окружения:

```bash
cp .env.example .env
# при необходимости отредактируйте значения в .env
docker compose up --build
```

### Проверка подключения
```bash
docker compose ps
docker compose logs -f db
docker compose logs -f backend
```

## Тестовые логины 
Инициализируются автоматически при первом запуске:
- `admin / admin123`
- `operator / operator123`
- `user / user123`

## Диаграммы
- UML: `docs/uml.md`
- ER + нормализация: `docs/database.md`
- Блок-схемы (ГОСТ): `docs/algorithms.md`
