package com.memory.memory_kotlin

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class Tasks (

    //重複のない一意のユーザーIDを作成
    @PrimaryKey open var id :String = UUID.randomUUID().toString(),
    //requiredは必須という意味
    @Required open var learnContext : String,
    open var created_at: Date = Date(),
    open var update_at: Date = Date()
    ) : RealmObject()

