# Book Library — DAO Pattern

**Лабораторная работа №3** — паттерн DAO (Data Access Object)

Десктопное JavaFX-приложение для управления библиотекой книг.

## Описание

Система управления каталогом книг в библиотеке. Реализует паттерн **DAO** (Data Access Object) для чёткого разделения бизнес-логики и слоя доступа к данным.

#Диаграммы

## Use Case Diagram
<img width="411" height="390" alt="ЛР3-use-case" src="https://github.com/user-attachments/assets/087d8280-23c0-4c0d-bf0e-ae897b10ca94" />

## Диаграмма классов
<img width="1395" height="670" alt="ЛР3-диаграмма-классов" src="https://github.com/user-attachments/assets/fa879847-a114-4bd4-90fa-b8701dba45c9" />

## Контекстная диаграмма
<img width="553" height="471" alt="ЛР3-контекстная-диаграмма" src="https://github.com/user-attachments/assets/4e0cd948-804a-4743-9dd6-dab8feeedac3" />

## ER-диаграмма
<img width="494" height="291" alt="ЛР3-ER-диаграмма" src="https://github.com/user-attachments/assets/614022b0-36ef-4a1e-a7aa-08d685a056bc" />


**MVP-спринт**: в качестве источника данных используется коллекция в памяти (`ListBookDAO`).

## Функционал

| Действие       | Описание                                      |
|----------------|-----------------------------------------------|
| Просмотр       | Список всех книг в таблице                    |
| Добавление     | Форма с полями: Название, Автор, Год издания |
| Удаление       | Удаление выбранной книги                      |
| Изменение статуса | Переключение «Доступна» ↔ «Выдана»         |
| Логирование    | Все операции записываются в `app.log` и отображаются в TextArea интерфейса |

##Запуск
Через IntelliJ IDEA
Run → Edit Configurations → Application
Main class: org.example.library.HelloApplication

##Тесты
Bash

## Архитектура

Многослойная архитектура **MVC + DAO**:

```bash
src/main/java/org/example/library/
├── model/
│   └── Book.java                    # Сущность книги
├── dao/
│   ├── BookDAO.java                 # Интерфейс DAO
│   └── ListBookDAO.java             # Реализация на основе List
├── factory/
│   └── BookFactory.java             # Фабрика для получения DAO
├── logger/
│   └── AppLogger.java               # Синглтон-логгер
├── controller/
│   └── BookController.java          # Бизнес-логика
├── HelloController.java             # FXML-контроллер UI
└── HelloApplication.java            # Главный класс JavaFX
