# CleverReflectionTask

# Используемый стек технологий:
Java 17, JDBC, Gradle, PostgreSQL 14, Git, JUnit 5, Mockito, Lombok, SnakeYAML, Mapstruct

# Краткое описание структуры:
Основная сущность - MusicBand в пакете еntity, её репрезентация в виде DTO - MusicBandDTO в пакете dto.   
Два основных слоя service и dao, вспомогательные - mapper.   
Service обращается к DAO через прокси, в котором происходит синхронизация с кэшем.    
Написано два алгоритма кэширования - LRU и LFU, оба параметризованы и имплементируют общий интерфейс Cache.  
Dao работает с базой данных через JDBC, подключен коннектор для PostgreSQL (зависимость указана в build.gradle),  
ConnctionPool кастомный и его реализация находится в слое dao в подпакете connectionpool.  
Валидация представлена в слое service в подпакете validation.  
Тажке реализованы CRUD операции.  
Проект содержит javadoc.  
На методы сервиса и кэша написаны unit тесты.

# UPDATE
# CleverPatternsTask
В дополнение к уже реализованным паттернам на предыдущей таске:  
Singleton;  
Builder (не Lombok,кастомный на валидации);  
Factory;  
Protection Proxy;  

В данной таске при реализации печати в PDF реализован:  
Decorator.  

Также новые методы сервисов покрыты unit-тестами. 
