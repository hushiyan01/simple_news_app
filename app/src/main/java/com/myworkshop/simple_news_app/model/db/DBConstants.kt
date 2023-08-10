package com.myworkshop.simple_news_app.model.db

object DBConstants {
    const val TABLE_NAME_NEWS = "news"
    const val DB_NAME = "db_news"
    const val DB_VERSION = 1

    val CREATE_TABLE_NEWS = """create table $TABLE_NAME_NEWS(
        id integer primary key autoincrement,
        author text,
        category text,
        description text,
        image text,
        language text,
        published text,
        title text,
        url text,
        gen_id text
    )
    """.trimMargin()


}