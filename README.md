# Movie Rental 

**Movie Rental** — это мобильное приложение, разработанное в рамках учебной практики.

Приложение разработано в Android Studio с использованием языка Kotlin. Оно представляет собой систему проката фильмов, которая позволяет просматривать каталог фильмов, искать их по различным критериям, просматривать подробную информацию о каждом фильме, добавлять, редактировать и удалять фильмы, а также управлять данными о клиентах, сотрудниках, оценках фильмов и арендах.

Приложение построено с использованием архитектурного подхода MVVM (Model-View-ViewModel). Для работы с базой данных SQLite используется библиотека Room, навигация между экранами реализована с помощью Navigation Component, связывание данных с элементами пользовательского интерфейса осуществляется через Data Binding, а загрузка и отображение изображений - с помощью библиотеки Glide. Управление жизненным циклом компонентов приложения обеспечивает Lifecycle.

База данных состоит из пяти таблиц: movies, employees, clients, movie_rentals, client_movie_ratings.

![image](https://media.discordapp.net/attachments/786554540442386432/1333522099381211189/IPapkIcmC5mbu5nXu5UYAFnnu6qbu6rfu6sTu7nRs2arm7jyogAAA7.png?ex=679932bc&is=6797e13c&hm=04395f6c79e6e23be60fc1f43f33b597fb5369563488ab92a53c0d5d2dd654ca&=&format=webp&quality=lossless)

Видео, демонстрирующее работу приложения: [видео](https://drive.google.com/file/d/1_HKwq-8N95N00YTqiRSd-W29khGIe_jy/view?usp=sharing)

![image](https://media.discordapp.net/attachments/786554540442386432/1333530212318707743/movie-rental-kotlin.png?ex=67993a4a&is=6797e8ca&hm=662af1cadcc4b22fc39be76a1d1144bf35b7f9d4be205939ec28db2786a5d07f&=&format=webp&quality=lossless&width=326&height=619)