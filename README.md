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

## Подробно для новичка (Windows)

### 1) Что значит «терминал в корне проекта»
Корень проекта — папка, где лежат `docker-compose.yml`, `backend/`, `frontend/`.

Пример:
```powershell
cd C:\Users\DasUser\Downloads\diplom-main\diplom-main
dir
```
В списке файлов должен быть `docker-compose.yml`.

### 2) Как запустить Docker Desktop
1. Откройте **Docker Desktop** через меню Пуск.
2. Дождитесь статуса **Engine running**.
3. Проверьте в PowerShell:
```powershell
docker version
docker compose version
```

### 3) Запуск проекта
Из корня проекта:
```powershell
docker compose up --build
```

### 4) Если видите ошибку `open //./pipe/dockerDesktopLinuxEngine`
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

### 5) Если была ошибка `npm ci ... package-lock.json`
Это исправлено в текущем проекте: frontend-сборка использует `npm install`, поэтому `package-lock.json` не обязателен.

### 6) Проверка, что всё запустилось
```powershell
docker compose ps
docker compose logs -f backend
docker compose logs -f frontend
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
