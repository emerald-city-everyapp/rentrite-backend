package com.emeraldeveryapp.rentritebackend.storage

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

class CloudSqlStorage {
    val INSTANCE_CONNECTION_NAME = System.getenv("INSTANCE_CONNECTION_NAME")
    val INSTANCE_UNIX_SOCKET = System.getenv("INSTANCE_UNIX_SOCKET")
    val DB_USER = System.getenv("DB_USER")
    val DB_PASS = System.getenv("DB_PASS")
    val DB_NAME = System.getenv("DB_NAME")
    
    fun createConnectionPool(): DataSource {
        val config = HikariConfig()
        
        config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME))
        config.setUsername(DB_USER)
        config.setPassword(DB_PASS)
        
        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory")
        config.addDataSourceProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME)
        config.addDataSourceProperty("unixSocketPath", INSTANCE_UNIX_SOCKET)
        
        return HikariDataSource(config)
    }
}